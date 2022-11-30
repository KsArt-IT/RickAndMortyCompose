package pro.ksart.rickandmorty.domain.usecase

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import pro.ksart.rickandmorty.R
import pro.ksart.rickandmorty.data.entity.CharacterDetail
import pro.ksart.rickandmorty.domain.entity.Results
import pro.ksart.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterDetailUserCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: CharacterRepository,
) {

    suspend operator fun invoke(param: Int): Results<CharacterDetail> {
        return try {
            val character = repository.requestCharacterById(param)
            Results.Success(character)
        } catch (e: Exception) {
            Results.Error(e.localizedMessage ?: context.getString(R.string.error_loading_data))
        }
    }

}
