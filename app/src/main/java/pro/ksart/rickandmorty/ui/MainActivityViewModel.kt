package pro.ksart.rickandmorty.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pro.ksart.rickandmorty.domain.usecase.ThemeUseCase
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val themeUseCase: ThemeUseCase
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
}
