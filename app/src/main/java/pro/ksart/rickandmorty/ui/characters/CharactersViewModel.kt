package pro.ksart.rickandmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import pro.ksart.rickandmorty.data.entity.CharacterRam
import pro.ksart.rickandmorty.data.entity.UiEvent
import pro.ksart.rickandmorty.domain.entity.Results
import pro.ksart.rickandmorty.domain.usecase.GetCharactersUseCase
import pro.ksart.rickandmorty.domain.usecase.SwitchDarkThemeUseCase
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    getCharactersUseCase: GetCharactersUseCase,
    private val switchDarkThemeUseCase: SwitchDarkThemeUseCase,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent<Unit>>()
    val uiEvent = _uiEvent.asSharedFlow()

    val characters: Flow<PagingData<CharacterRam>> =
        getCharactersUseCase().mapNotNull { result ->
            when (result) {
                is Results.Success -> result.data
                is Results.Loading -> null
                is Results.Error -> {
                    _uiEvent.emit(UiEvent.Error(result.message))
                    null
                }
            }
        }
            .cachedIn(viewModelScope)

    fun switchDarkTheme() {
        viewModelScope.launch {
            switchDarkThemeUseCase()
        }
    }
}
