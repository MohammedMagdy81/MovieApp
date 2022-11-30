package ddd.magdy.movieapp.repository

import ddd.magdy.movieapp.api.ApiService
import javax.inject.Inject


class MovieRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getPopularMovieList(page: Int) = apiService.getPopularList(page)

    suspend fun getMovieDetailById(movieId: Int) = apiService.getMovieDetailsById(movieId)
}