package pro.ksart.rickandmorty.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pro.ksart.rickandmorty.ui.character_detail.CharacterDetailScreen
import pro.ksart.rickandmorty.ui.characters.CharactersScreen
import pro.ksart.rickandmorty.ui.components.RickMortyAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RickMortyApp(
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit = {},
    containerColor: Color = MaterialTheme.colorScheme.background,
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = when (backStackEntry?.destination?.route ?: Screen.CharactersScreen.route) {
        Screen.CharactersScreen.route -> Screen.CharactersScreen
        Screen.CharacterDetailScreen.route -> Screen.CharacterDetailScreen
        else -> Screen.CharacterDetailScreen
    }
    val titleInit = stringResource(currentScreen.title)
    var titleApp by rememberSaveable { mutableStateOf(titleInit) }

    Scaffold(
        topBar = {
            RickMortyAppBar(
                title = titleApp,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                onActionClick = onActionClick,
            )
        },
        containerColor = containerColor,
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.CharactersScreen.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(
                route = Screen.CharactersScreen.route
            ) {
                CharactersScreen(
                    onClick = { id ->
                        navController.navigate("${Screen.CharacterDetailScreen.route}/$id")
                    }
                )
            }
            composable(
                route = Screen.CharacterDetailScreen.routeWithoutArgs,
                arguments = Screen.CharacterDetailScreen.arguments,
            ) { navBackStackEntry ->
                CharacterDetailScreen(
                    characterId = navBackStackEntry.arguments
                        ?.getInt(Screen.CharacterDetailScreen.argument) ?: -1,
                    onTitleChanged = { title -> titleApp = title },
                    back = { navController.navigateUp() },
                )
            }
        }
    }
}
