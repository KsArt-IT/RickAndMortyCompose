package pro.ksart.rickandmorty.domain.usecase

import android.content.Context
import androidx.paging.PagingData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import pro.ksart.rickandmorty.R
import pro.ksart.rickandmorty.data.entity.CharacterRam
import pro.ksart.rickandmorty.domain.entity.Results
import pro.ksart.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: CharacterRepository,
) {

    operator fun invoke(): Flow<Results<PagingData<CharacterRam>>> =
        repository.requestCharacters().map { data ->
            Results.Success(data)
        }.catch { error ->
            Results.Error(error.localizedMessage ?: context.getString(R.string.error_loading_data))
        }

}
