package pro.ksart.rickandmorty.ui.character_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EpisodeItem(
    id: Int,
    name: String,
    type: String,
    dimension: String,
    created: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "$id: $name",
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = type,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
