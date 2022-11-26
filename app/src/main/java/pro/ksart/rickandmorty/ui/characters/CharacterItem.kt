package pro.ksart.rickandmorty.ui.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pro.ksart.rickandmorty.R
import pro.ksart.rickandmorty.data.entity.CharacterRam
import pro.ksart.rickandmorty.ui.components.CoilImageWithLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterItem(
    character: CharacterRam,
    onClick: (CharacterRam) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = { onClick(character) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            CoilImageWithLoading(
                url = character.image,
                contentDescription = "Character",
                modifier = Modifier
                    .size(128.dp)
                    .align(CenterVertically)
            )
            Column(
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(8.dp)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = character.status,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(R.string.last_location_title),
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = character.location.name,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

