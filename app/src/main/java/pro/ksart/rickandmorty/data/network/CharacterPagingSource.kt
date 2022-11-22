package pro.ksart.rickandmorty.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import pro.ksart.rickandmorty.data.entity.CharacterRam
import retrofit2.HttpException
import java.io.IOException

class CharacterPagingSource(
    private val service: CharacterService,
) : PagingSource<Int, CharacterRam>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterRam>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterRam> {
        val position = params.key ?: CharacterService.STARTING_PAGE_INDEX
        return try {
            val response = service.getCharacters(page = position)
            if (response.isSuccessful) {
                val result = response.body()?.characterRams ?: emptyList()
                val nextKey = if (result.isEmpty()) null else position + 1
                val prevKey = if (position == CharacterService.STARTING_PAGE_INDEX) null
                else position - 1
                LoadResult.Page(
                    data = result,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
