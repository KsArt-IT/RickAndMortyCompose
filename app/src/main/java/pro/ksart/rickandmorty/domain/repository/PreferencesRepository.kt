package pro.ksart.rickandmorty.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface PreferencesRepository {

    val isDarkThemeState: StateFlow<Boolean>

    suspend fun switchDarkTheme()

    suspend fun registerChangeSettings()

    fun unregisterChangeSettings()

}
