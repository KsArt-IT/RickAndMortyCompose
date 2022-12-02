package pro.ksart.rickandmorty.data.network

import pro.ksart.rickandmorty.data.entity.CharacterDetail
import pro.ksart.rickandmorty.data.entity.CharacterResponse
import pro.ksart.rickandmorty.data.entity.EpisodeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    suspend fun getCharacters(
        @Query(PARAMS_PAGE) page: Int,
    ): Response<CharacterResponse>

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path(PARAMS_ID) id: Int
    ): CharacterDetail

    @GET("episode")
    suspend fun getEpisodes(
        @Query(PARAMS_PAGE) page: Int,
    ): Response<EpisodeResponse>

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
        const val STARTING_PAGE_INDEX = 1
        const val PAGE_SIZE = 20
        const val PARAMS_PAGE = "page"
        const val PARAMS_ID = "id"
    }
}
