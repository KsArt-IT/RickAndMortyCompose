package pro.ksart.rickandmorty.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import pro.ksart.rickandmorty.ui.navigation.RickMortyApp
import pro.ksart.rickandmorty.ui.theme.RickAndMortyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()

            RickAndMortyTheme(darkTheme = isDarkTheme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                RickMortyApp(
                    modifier = Modifier.fillMaxSize(),
                    onActionClick = { viewModel.switchDarkTheme() },
                )
            }
        }
    }
}
