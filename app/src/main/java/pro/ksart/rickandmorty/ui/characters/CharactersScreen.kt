package pro.ksart.rickandmorty.ui.characters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import pro.ksart.rickandmorty.R
import pro.ksart.rickandmorty.data.entity.UiEvent
import pro.ksart.rickandmorty.ui.components.RickMortyAppBar
import pro.ksart.rickandmorty.ui.showToast

@Composable
fun CharactersScreen(
    onClick: (Int) -> Unit,
    viewModel: CharactersViewModel = hiltViewModel()
) {
    var topAppBarSize by remember { mutableStateOf(0) }
    val characters = viewModel.characters.collectAsLazyPagingItems()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiEventFlow = viewModel.uiEvent
    val uiEventFlowLifecycleAware = remember(uiEventFlow, lifecycleOwner) {
        uiEventFlow.flowWithLifecycle(lifecycleOwner.lifecycle)
    }

    LaunchedEffect(key1 = "CharactersScreenKey") {
        uiEventFlowLifecycleAware.collect { event ->
            when (event) {
                is UiEvent.Success -> {}
                is UiEvent.Error -> showToast(context, event.message)
                is UiEvent.Toast -> showToast(context, event.stringId)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = LocalDensity.current.run { topAppBarSize.toDp() })
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
        ) {
            items(characters) { character ->
                character?.let {
                    CharacterItem(
                        character = it,
                        onClick = onClick
                    )
                }
            }
            characters.apply {
                when {
                    // ???? ?????????????????? ???????????????? ???? ???????? ??????????
                    loadState.refresh is LoadState.Loading -> {
                        //You can add modifier to manage load state when first time response page is loading
                        item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val e = characters.loadState.refresh as LoadState.Error
                        item {
                            ErrorView(
                                message = e.error.localizedMessage!!,
                                modifier = Modifier.fillParentMaxSize(),
                                onClickRetry = { retry() }
                            )
                        }
                    }
                    // ???????????????????? ??????????????
                    loadState.append is LoadState.Loading -> {
                        //You can add modifier to manage load state when next response page is loading
                        item { LoadingItem() }
                    }

                    loadState.append is LoadState.Error -> {
                        //You can use modifier to show error message
                        val e = characters.loadState.append as LoadState.Error
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
    RickMortyAppBar(
        title = stringResource(id = R.string.app_name),
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { topAppBarSize = it.height },
        onActionClick = { viewModel.switchDarkTheme() }
    )
}
