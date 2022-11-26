package pro.ksart.rickandmorty.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import pro.ksart.rickandmorty.R

@Composable
fun CoilImageWithLoading(url: String, contentDescription: String, modifier: Modifier) {
    SubcomposeAsyncImage(
        model = url,
        loading = {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_loading),
                    contentDescription = stringResource(id = R.string.loading_description),
                )
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp)
                )
            }
        },
        error = {
            Image(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = stringResource(id = R.string.error_description),
            )
        },
        contentDescription = contentDescription,
        modifier = modifier
    )
}
