package com.acuminx.moviehub.data.remote

import com.acuminx.moviehub.data.model.MovieDetailDto
import com.acuminx.moviehub.data.model.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "YOUR_API_KEY" // In prod, use BuildKonfig or Interceptor
    ): MovieResponseDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = "YOUR_API_KEY"
    ): MovieResponseDto

    @GET("movie/{movie_id}?append_to_response=credits")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "YOUR_API_KEY"
    ): MovieDetailDto
}