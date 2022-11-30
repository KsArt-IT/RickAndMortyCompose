package pro.ksart.rickandmorty.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pro.ksart.rickandmorty.ui.character_detail.CharacterDetailScreen
import pro.ksart.rickandmorty.ui.characters.CharactersScreen

internal fun NavGraphBuilder.navGraph(
    navController: NavController
) {
    composable(
        route = Screen.CharactersScreen.route
    ) {
        CharactersScreen(
            onClick = { id ->
                navController.navigate(
                    Screen.CharacterDetailScreen.routeWithoutArgs + "$id",
                )
            }
        )
    }
    composable(
        route = Screen.CharacterDetailScreen.route,
        arguments = listOf(navArgument("id") { type = NavType.IntType }),
    ) { navBackStackEntry ->
        CharacterDetailScreen(
            navBackStackEntry.arguments?.getInt("id") ?: -1,
            navController,
        )
    }
}
