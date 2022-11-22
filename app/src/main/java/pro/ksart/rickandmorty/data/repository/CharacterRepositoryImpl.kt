package pro.ksart.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import pro.ksart.rickandmorty.data.entity.CharacterRam
import pro.ksart.rickandmorty.data.network.CharacterPagingSource
import pro.ksart.rickandmorty.data.network.CharacterService
import pro.ksart.rickandmorty.di.IoDispatcher
import pro.ksart.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val service: CharacterService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : CharacterRepository {

    override fun requestCharacters(): Flow<PagingData<CharacterRam>> = Pager(
        config = PagingConfig(
            pageSize = CharacterService.PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { CharacterPagingSource(service) },
    ).flow.flowOn(dispatcher)

    override suspend fun requestCharacterById(id: Long): CharacterRam = withContext(dispatcher) {
        service.getCharacter(id)
    }
}
