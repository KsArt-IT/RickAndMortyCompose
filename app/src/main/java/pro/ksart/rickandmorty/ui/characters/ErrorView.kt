package pro.ksart.rickandmorty.ui.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pro.ksart.rickandmorty.R

@Composable
fun ErrorView(
    modifier: Modifier,
    message: String,
    onClickRetry: () -> Unit,
) {
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = onClickRetry
        ) {
            Text(text = stringResource(id = R.string.retry_button_text))
        }

    }
}
