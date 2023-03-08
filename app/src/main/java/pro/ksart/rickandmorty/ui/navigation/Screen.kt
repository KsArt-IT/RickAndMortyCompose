package pro.ksart.rickandmorty.ui.navigation

import androidx.annotation.StringRes
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import pro.ksart.rickandmorty.R

sealed class Screen(
    val route: String,
    val argument: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    @StringRes val title: Int,
) {
    val routeWithoutArgs: String = "$route/{$argument}"

    object CharactersScreen : Screen(
        route = "CharactersScreen",
        argument = "",
        title = R.string.app_name,
    )

    object CharacterDetailScreen : Screen(
        route = "CharacterDetailScreen",
        argument = "id",
        arguments = listOf(navArgument("id") { type = NavType.IntType }),
        title = R.string.character_title
    )
}
