package pro.ksart.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import pro.ksart.rickandmorty.data.entity.CharacterDetail
import pro.ksart.rickandmorty.data.entity.CharacterRam
import pro.ksart.rickandmorty.data.entity.Episode
import pro.ksart.rickandmorty.data.network.CharacterPagingSource
import pro.ksart.rickandmorty.data.network.CharacterService
import pro.ksart.rickandmorty.data.network.EpisodePagingSource
import pro.ksart.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject
import javax.inject.Named

class CharacterRepositoryImpl @Inject constructor(
    private val service: CharacterService,
    @Named("IoDispatcher") private val dispatcher: CoroutineDispatcher,
) : CharacterRepository {

    override fun requestCharacters(): Flow<PagingData<CharacterRam>> = Pager(
        config = PagingConfig(
            pageSize = CharacterService.PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { CharacterPagingSource(service) },
    ).flow.flowOn(dispatcher)

    override suspend fun requestCharacterById(id: Int): CharacterDetail = withContext(dispatcher) {
        service.getCharacterById(id)
    }

    override fun requestEpisodes(): Flow<PagingData<Episode>> = Pager(
        config = PagingConfig(
            pageSize = CharacterService.PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { EpisodePagingSource(service) },
    ).flow.flowOn(dispatcher)
}
