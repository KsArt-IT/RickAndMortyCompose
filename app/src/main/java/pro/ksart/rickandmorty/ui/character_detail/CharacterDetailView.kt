package pro.ksart.rickandmorty.ui.character_detail

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.ksart.rickandmorty.R
import pro.ksart.rickandmorty.data.entity.CharacterDetail

@Composable
fun CharacterDetailView(characterDetail: CharacterDetail) {
    CharacterHeader(characterDetail)
    CharacterStat(
        statId = R.string.character_last_location_title,
        stat = characterDetail.location.name
    )
    CharacterStat(statId = R.string.character_type_title, stat = characterDetail.type)
    CharacterStat(statId = R.string.character_created_title, stat = characterDetail.created)
    CharacterStat(statId = R.string.character_origin_title, stat = characterDetail.origin.name)
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
    Text(
        text = character.name,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp),
    )
    AsyncImage(
        model = R.drawable.separator,
        contentDescription = stringResource(id = R.string.character_image_description),
        modifier = Modifier.fillMaxWidth(),
        alignment = Alignment.TopStart,
        contentScale = ContentScale.None,
        alpha = 0.75f,
    )
    Text(
        text = stringResource(id = R.string.character_status_title),
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
    ) {
        Icon(
            Icons.Filled.Person, contentDescription = null,
            tint = if (character.status == "Alive") Color.Green else Color.Red
        )
        Text(
            text = "${character.status} - ${character.species} (${character.gender})",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun CharacterStat(@StringRes statId: Int, stat: String) {
    Text(
        text = stringResource(id = statId),
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp),
    )
    Text(
        text = stat.ifBlank { stringResource(id = R.string.unknown_text) },
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(horizontal = 8.dp),
    )
}
