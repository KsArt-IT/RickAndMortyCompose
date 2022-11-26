package pro.ksart.rickandmorty.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pro.ksart.rickandmorty.ui.characters.CharactersScreen

internal fun NavGraphBuilder.navGraph(
    navController: NavController
) {
    composable(
        route = Screen.CharactersScreen.route
    ) {
        CharactersScreen(
            onClick = { character ->
                navController.navigate(
                    Screen.CharacterDetailScreen.routeWithoutArgs + "${character.id}",
                )
            }
        )
    }
    composable(
        route = Screen.CharacterDetailScreen.route
    ) {

    }
}
