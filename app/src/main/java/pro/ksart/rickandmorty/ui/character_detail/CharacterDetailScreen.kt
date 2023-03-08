package pro.ksart.rickandmorty.ui.character_detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import pro.ksart.rickandmorty.R
import pro.ksart.rickandmorty.data.entity.CharacterDetail
import pro.ksart.rickandmorty.domain.entity.UiEvent
import pro.ksart.rickandmorty.domain.entity.UiState
import pro.ksart.rickandmorty.ui.characters.ErrorItem
import pro.ksart.rickandmorty.ui.characters.ErrorView
import pro.ksart.rickandmorty.ui.characters.LoadingItem
import pro.ksart.rickandmorty.ui.characters.LoadingView
import pro.ksart.rickandmorty.ui.components.showToast

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    onTitleChanged: (String) -> Unit,
    back: () -> Unit,
    viewModel: CharacterDetailViewModel = hiltViewModel(),
) {
    // если id не определен, вернемся назад
    if (characterId == -1) back()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val episodes = viewModel.episodes.collectAsLazyPagingItems()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiEventFlow = viewModel.uiEvent
    val uiEventFlowLifecycleAware = remember(uiEventFlow, lifecycleOwner) {
        uiEventFlow.flowWithLifecycle(lifecycleOwner.lifecycle)
    }

    LaunchedEffect(key1 = "CharacterDetailScreenKey") {
        uiEventFlowLifecycleAware.collect { event ->
            when (event) {
                is UiEvent.Success -> {}
                is UiEvent.Error -> showToast(context, event.message)
                is UiEvent.Toast -> showToast(context, event.stringId)
            }
        }
    }

    viewModel.getCharacterDetail(characterId)

    when (uiState) {
        is UiState.Success -> {
            val characterDetail = (uiState as UiState.Success<CharacterDetail>).data
            onTitleChanged(characterDetail.name)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
            ) {
                item {
                    CharacterDetailView(characterDetail)
                }
                // Episodes
                val characterEpisodes = characterDetail.episode.mapNotNull { url ->
                    url.toUri().lastPathSegment?.toIntOrNull()
                }
                item {
                    Text(
                        text = stringResource(id = R.string.episode_title),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            top = 8.dp,
                            end = 8.dp,
                            bottom = 4.dp
                        ),
                    )
                }
                items(episodes) { episode ->
                    episode?.takeIf { element ->
                        characterEpisodes.contains(element.id)
                    }?.let {
                        EpisodeItem(
                            name = it.name,
                            airDate = it.airDate,
                            episode = it.episode,
                        )
                    }
                }
                episodes.apply {
                    when {
                        // по состоянию показать на весь экран
                        loadState.refresh is LoadState.Loading -> {
                            //You can add modifier to manage load state when first time response page is loading
                            item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val e = episodes.loadState.refresh as LoadState.Error
                            item {
                                ErrorView(
                                    message = e.error.localizedMessage!!,
                                    modifier = Modifier.fillParentMaxSize(),
                                    onClickRetry = { retry() }
                                )
                            }
                        }
                        // дозагрузка данных
                        // показывать элемент
                        loadState.append is LoadState.Loading -> {
                            //You can add modifier to manage load state when next response page is loading
                            item { LoadingItem() }
                        }

                        loadState.append is LoadState.Error -> {
                            //You can use modifier to show error message
                            val e = episodes.loadState.append as LoadState.Error
                            item {
                                ErrorItem(
                                    message = e.error.localizedMessage!!,
                                    onClickRetry = { retry() }
                                )
                            }
                        }
                    }
                }
            }
        }

        is UiState.Loading -> {
            onTitleChanged(stringResource(id = R.string.loading_text))
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        is UiState.Error -> {}
    }
}
