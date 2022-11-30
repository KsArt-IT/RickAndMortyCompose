package pro.ksart.rickandmorty.domain.usecase

import pro.ksart.rickandmorty.domain.repository.PreferencesRepository
import javax.inject.Inject

class ThemeUseCase @Inject constructor(
    private val repository: PreferencesRepository,
) {

    val isDarkTheme = repository.isDarkThemeState

    suspend fun registerChangeSettings() {
        try {
            repository.registerChangeSettings()
        } catch (ignore: Exception) {

        }
    }

    fun unregisterChangeSettings() {
        try {
            repository.unregisterChangeSettings()
        } catch (ignore: Exception) {

        }
    }
}

