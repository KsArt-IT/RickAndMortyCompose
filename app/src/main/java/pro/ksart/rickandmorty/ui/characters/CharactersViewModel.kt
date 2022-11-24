package pro.ksart.rickandmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pro.ksart.rickandmorty.data.entity.CharacterRam
import pro.ksart.rickandmorty.data.entity.UiAction
import pro.ksart.rickandmorty.data.entity.UiEvent
import pro.ksart.rickandmorty.data.entity.UiState
import pro.ksart.rickandmorty.domain.entity.Results
import pro.ksart.rickandmorty.domain.usecase.GetCharactersUseCase
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    getCharacters: GetCharactersUseCase,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent<CharacterRam>>()
    val uiEvent = _uiEvent.asSharedFlow()

    val uiState: StateFlow<UiState<PagingData<CharacterRam>>> =
        getCharacters().mapNotNull { state ->
            when (state) {
                is Results.Success -> state.data
                is Results.Error -> {
                    _uiEvent.emit(UiEvent.Error(state.message))
                    null
                }

                is Results.Loading -> null
            }
        }
            .cachedIn(viewModelScope)
            .map { data ->
                UiState.Success(data)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = UiState.Loading
            )

    val uiAction: (UiAction<CharacterRam>) -> Unit = { action ->
        viewModelScope.launch {
            when (action) {
                is UiAction.Click -> _uiEvent.emit(UiEvent.Success(action.data))
                is UiAction.Scroll -> {}
            }
        }
    }
}
