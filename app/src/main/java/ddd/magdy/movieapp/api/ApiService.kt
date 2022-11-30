package ddd.magdy.movieapp.api

import ddd.magdy.movieapp.response.MovieDetailsResponse
import ddd.magdy.movieapp.response.MovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularList(@Query("page") page: Int): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailsById(@Path("movie_id") id: Int): Response<MovieDetailsResponse>
}