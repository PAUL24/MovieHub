package com.acuminx.moviehub.domain.repository

import com.acuminx.moviehub.core.network.Resource
import com.acuminx.moviehub.data.model.MovieDetailDto
import com.acuminx.moviehub.data.model.MovieDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(page: Int): Flow<Resource<List<MovieDto>>>
    fun searchMovies(query: String): Flow<Resource<List<MovieDto>>>
    fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetailDto>>
}