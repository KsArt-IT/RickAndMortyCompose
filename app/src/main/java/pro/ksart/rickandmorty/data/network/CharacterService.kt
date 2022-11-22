package pro.ksart.rickandmorty.data.network

import pro.ksart.rickandmorty.data.entity.CharacterRam
import pro.ksart.rickandmorty.data.entity.CharacterResponse
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
    suspend fun getCharacter(
        @Path(PARAMS_ID) id: Long
    ): CharacterRam

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
        const val STARTING_PAGE_INDEX = 1
        const val PAGE_SIZE = 40
        const val PARAMS_PAGE = "page"
        const val PARAMS_ID = "id"
    }
}
