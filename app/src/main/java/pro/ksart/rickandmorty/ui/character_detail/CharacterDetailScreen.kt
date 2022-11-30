package pro.ksart.rickandmorty.ui.character_detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow
import pro.ksart.rickandmorty.R
import pro.ksart.rickandmorty.data.entity.CharacterDetail
import pro.ksart.rickandmorty.data.entity.UiEvent
import pro.ksart.rickandmorty.data.entity.UiState
import pro.ksart.rickandmorty.ui.characters.LoadingView
import pro.ksart.rickandmorty.ui.components.RickMortyAppBar

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun CharacterDetailScreen(
    characterId: Int,
    navController: NavController,
    viewModel: CharacterDetailViewModel = hiltViewModel(),
) {
    var topAppBarSize by remember { mutableStateOf(0) }
    val unknown = stringResource(id = R.string.unknown_text)
    var topAppBarTitle by remember { mutableStateOf(unknown) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEvent by viewModel.uiEvent.collectAsStateWithLifecycle(UiEvent.Success(Unit))

    // если id не определен, вернемся назад
    if (characterId == -1) navController.navigateUp()

    viewModel.getCharacterDetail(characterId)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = LocalDensity.current.run { topAppBarSize.toDp() })
    ) {

        when (uiState) {
            is UiState.Success -> {
                val characterDetail = (uiState as UiState.Success<CharacterDetail>).data
                topAppBarTitle = characterDetail.name
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                ) {
                    item {
                        showCharacterDetail(characterDetail)
                    }
                }
            }

            is UiState.Loading -> {
                topAppBarTitle = stringResource(id = R.string.loading_text)
                LoadingView(modifier = Modifier.fillMaxSize())
            }

            is UiState.Error -> {
//                Toast.makeText(this, (uiState as UiState.Error).message)
            }
        }
    }
    RickMortyAppBar(
        title = topAppBarTitle,
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { topAppBarSize = it.height },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        onActionClick = { viewModel.switchDarkTheme() }
    )
}

@Composable
private fun showCharacterDetail(characterDetail: CharacterDetail) {
    CharacterHeader(characterDetail)
    CharacterStat(statLabel = "Created", stat = characterDetail.created)
    CharacterStat(statLabel = "Type", stat = characterDetail.type.ifBlank { "-" })
    CharacterStat(statLabel = "Gender", stat = characterDetail.gender)
    CharacterStat(statLabel = "Origin", stat = characterDetail.origin.name)
    CharacterStat(statLabel = "Location", stat = characterDetail.location.name)
    CharacterEpisode(characterDetail.episode)
}

@Composable
fun CharacterHeader(character: CharacterDetail) {
    AsyncImage(
        model = character.image,
        contentDescription = stringResource(id = R.string.character_image_description),
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(1f)
            .aspectRatio(1f)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Icon(
            Icons.Filled.Person, contentDescription = null,
            tint = if (character.status == "Alive") Color.Green else Color.Red
        )
        Text(
            text = "${character.status} - ${character.species}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }
}

@Composable
fun CharacterStat(statLabel: String, stat: String) {
    Text(
        text = "$statLabel: ${stat.ifBlank { stringResource(id = R.string.unknown_text) }}",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(8.dp),
    )
}

@Composable
fun CharacterEpisode(episodes: List<String>) {
    Text(
        text = "${stringResource(id = R.string.episode_title)}${if (episodes.isEmpty()) " -" else ""}",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(8.dp),
    )
    if (episodes.isNotEmpty()) {
        FlowRow(
            mainAxisSpacing = 10.dp,
            crossAxisSpacing = 10.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            episodes.forEach { url ->
                url.toUri().lastPathSegment?.let { episode ->
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .padding(10.dp)
                    ) {
                        Text(
                            text = episode,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
