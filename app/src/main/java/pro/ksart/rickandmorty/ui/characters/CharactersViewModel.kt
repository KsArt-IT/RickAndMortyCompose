package pro.ksart.rickandmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import pro.ksart.rickandmorty.data.entity.CharacterRam
import pro.ksart.rickandmorty.domain.entity.Results
import pro.ksart.rickandmorty.domain.usecase.GetCharactersUseCase
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    getCharacters: GetCharactersUseCase,
) : ViewModel() {

    val characters: Flow<PagingData<CharacterRam>> =
        getCharacters().mapNotNull { result ->
            when (result) {
                is Results.Success -> result.data
                is Results.Error -> null
                is Results.Loading -> null
            }
        }
            .cachedIn(viewModelScope)
}
