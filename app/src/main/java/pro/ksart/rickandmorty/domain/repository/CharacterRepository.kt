package pro.ksart.rickandmorty.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pro.ksart.rickandmorty.data.entity.CharacterDetail
import pro.ksart.rickandmorty.data.entity.CharacterRam
import pro.ksart.rickandmorty.data.entity.Episode

interface CharacterRepository {
    fun requestCharacters(): Flow<PagingData<CharacterRam>>
    fun requestEpisodes(): Flow<PagingData<Episode>>
    suspend fun requestCharacterById(id: Int): CharacterDetail
}
