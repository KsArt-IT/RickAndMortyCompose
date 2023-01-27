package pro.ksart.rickandmorty.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import pro.ksart.rickandmorty.data.entity.Episode
import retrofit2.HttpException
import java.io.IOException

class EpisodePagingSource(
    private val service: CharacterService,
) : PagingSource<Int, Episode>() {

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        val position = params.key ?: CharacterService.STARTING_PAGE_INDEX
        return try {
            val response = service.getEpisodes(page = position)
            if (response.isSuccessful) {
                val result = response.body()?.episodes ?: emptyList()
                val pageNext = response.body()?.info?.next
                val nextKey = if (result.isEmpty() || pageNext.isNullOrBlank()) null
                else position + 1
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
