package pro.ksart.rickandmorty.ui.character_detail

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.ksart.rickandmorty.R
import pro.ksart.rickandmorty.data.entity.CharacterDetail

@Composable
fun CharacterDetailView(characterDetail: CharacterDetail) {
    CharacterHeader(characterDetail)
    CharacterStat(statLabel = "Created", stat = characterDetail.created)
    CharacterStat(statLabel = "Type", stat = characterDetail.type.ifBlank { "-" })
    CharacterStat(statLabel = "Gender", stat = characterDetail.gender)
    CharacterStat(statLabel = "Origin", stat = characterDetail.origin.name)
    CharacterStat(statLabel = "Location", stat = characterDetail.location.name)
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
