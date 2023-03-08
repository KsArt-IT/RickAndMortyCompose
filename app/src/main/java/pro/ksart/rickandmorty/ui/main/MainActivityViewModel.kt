package pro.ksart.rickandmorty.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pro.ksart.rickandmorty.domain.usecase.SwitchDarkThemeUseCase
import pro.ksart.rickandmorty.domain.usecase.ThemeUseCase
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val themeUseCase: ThemeUseCase,
    private val switchDarkThemeUseCase: SwitchDarkThemeUseCase,
) : ViewModel() {

    val isDarkTheme = themeUseCase.isDarkTheme

    init {
        viewModelScope.launch {
            themeUseCase.registerChangeSettings()
        }
    }

    override fun onCleared() {
        themeUseCase.unregisterChangeSettings()
        super.onCleared()
    }

    fun switchDarkTheme() {
        viewModelScope.launch {
            switchDarkThemeUseCase()
        }
    }

}
