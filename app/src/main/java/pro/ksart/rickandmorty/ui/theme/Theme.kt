package pro.ksart.rickandmorty.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = BlueGreyDark900,
    onPrimary = BlueGreyLight300,
    secondary = BlueGreyDark800,
    onSecondary = BlueGreyLight300,
    tertiary = BlueGrey800,
    onTertiary = BlueGreyLight300,
    background = BlueGreyLight900,
    onBackground = BlueGreyLight300,
    surface = BlueGrey900,
    onSurface = BlueGreyLight300,
)

private val LightColorScheme = lightColorScheme(
    primary = BlueGreyDark900,
    onPrimary = BlueGrey800,
    secondary = BlueGreyDark300,
    onSecondary = BlueGrey800,
    tertiary = BlueGrey200,
    onTertiary = BlueGrey800,
    background = BlueGreyLight300,
    onBackground = BlueGrey800,
    surface = BlueGrey300,
    onSurface = BlueGrey800,
)

@Composable
fun RickAndMortyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
