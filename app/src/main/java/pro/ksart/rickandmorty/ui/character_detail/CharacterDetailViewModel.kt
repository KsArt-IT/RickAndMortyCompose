package pro.ksart.rickandmorty.ui.character_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import pro.ksart.rickandmorty.data.entity.CharacterDetail
import pro.ksart.rickandmorty.data.entity.Episode
import pro.ksart.rickandmorty.data.entity.UiEvent
import pro.ksart.rickandmorty.data.entity.UiState
import pro.ksart.rickandmorty.domain.entity.Results
import pro.ksart.rickandmorty.domain.usecase.GetCharacterDetailUserCase
import pro.ksart.rickandmorty.domain.usecase.GetEpisodesUseCase
import pro.ksart.rickandmorty.domain.usecase.SwitchDarkThemeUseCase
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUserCase: GetCharacterDetailUserCase,
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val switchDarkThemeUseCase: SwitchDarkThemeUseCase,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent<Nothing>>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _uiState = MutableStateFlow<UiState<CharacterDetail>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getCharacterDetail(id: Int) {
        viewModelScope.launch {
            when (val result = getCharacterDetailUserCase(id)) {
                is Results.Success -> _uiState.value = UiState.Success(result.data)
                is Results.Error -> _uiEvent.emit(UiEvent.Error(result.message))
                is Results.Loading -> _uiState.value = UiState.Loading
            }
        }
    }

    val episodes: Flow<PagingData<Episode>> =
        getEpisodesUseCase().mapNotNull { result ->
            when (result) {
                is Results.Success -> result.data
                is Results.Error -> null
                is Results.Loading -> null
            }
        }
            .cachedIn(viewModelScope)

    fun switchDarkTheme() {
        viewModelScope.launch {
            switchDarkThemeUseCase()
        }
    }

}
