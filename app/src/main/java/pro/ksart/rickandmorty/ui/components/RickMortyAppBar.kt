package pro.ksart.rickandmorty.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pro.ksart.rickandmorty.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RickMortyAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 8.dp,
    onActionClick: () -> Unit
) {
    Surface(
        color = backgroundColor,
        tonalElevation = elevation,
        shadowElevation = elevation,
        modifier = modifier,
    ) {
        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = navigationIcon,
            actions = {
                IconButton(onClick = { onActionClick() }) {
                    Icon(Icons.Filled.Settings, stringResource(id = R.string.theme_light_dark))
                }
            },
        )
    }
}
