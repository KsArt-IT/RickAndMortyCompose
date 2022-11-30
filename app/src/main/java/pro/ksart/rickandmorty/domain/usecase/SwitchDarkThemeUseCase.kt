package pro.ksart.rickandmorty.domain.usecase

import pro.ksart.rickandmorty.domain.repository.PreferencesRepository
import javax.inject.Inject

class SwitchDarkThemeUseCase @Inject constructor(
    private val repository: PreferencesRepository,
) {
    suspend operator fun invoke() {
        try {
            repository.switchDarkTheme()
        } catch (ignore: Exception) {
        }
    }
}
