package pro.ksart.rickandmorty.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pro.ksart.rickandmorty.data.entity.CharacterRam

interface CharacterRepository {
    fun requestCharacters(): Flow<PagingData<CharacterRam>>
    suspend fun requestCharacterById(id: Long): CharacterRam
}
