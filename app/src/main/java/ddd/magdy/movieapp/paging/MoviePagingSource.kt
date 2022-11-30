package ddd.magdy.movieapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ddd.magdy.movieapp.repository.MovieRepository
import ddd.magdy.movieapp.response.MovieResponse
import ddd.magdy.movieapp.response.ResultsItem
import retrofit2.HttpException
import java.lang.Exception

class MoviePagingSource(private val repository: MovieRepository) :
    PagingSource<Int, ResultsItem>() {
    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.getPopularMovieList(currentPage)
            val data = response.body()?.results
            val responseData = mutableListOf<ResultsItem>()
            responseData.addAll(data!!)
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }


}
