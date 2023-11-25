package pro.ksart.rickandmorty.ui.characters

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import pro.ksart.rickandmorty.domain.entity.UiEvent
import pro.ksart.rickandmorty.ui.components.showToast

@Composable
fun CharactersScreen(
    onClick: (Int) -> Unit,
    viewModel: CharactersViewModel = hiltViewModel()
) {
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

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        state = rememberLazyListState(),
    ) {
/*
        items(characters.itemCount) { index ->
            characters[index]?.let {
                CharacterItem(
                    character = it,
                    onClick = onClick
                )
            }
        }
*/
        items(
            items = characters.itemSnapshotList.items,
            key = { it.id }
        ) { character ->
            CharacterItem(
                character = character,
                onClick = onClick
            )
        }
        characters.apply {
            when {
                // по состоянию показать на весь экран
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
                // показывать элемент
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
