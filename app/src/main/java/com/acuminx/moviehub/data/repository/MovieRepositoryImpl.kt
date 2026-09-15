package com.acuminx.moviehub.data.repository

import com.acuminx.moviehub.core.network.Resource
import com.acuminx.moviehub.data.model.MovieDetailDto
import com.acuminx.moviehub.data.model.MovieDto
import com.acuminx.moviehub.data.remote.TmdbApi
import com.acuminx.moviehub.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbApi
) : MovieRepository {

    override fun getPopularMovies(page: Int): Flow<Resource<List<MovieDto>>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getPopularMovies(page)
            emit(Resource.Success(response.results))
        } catch (e: Exception) {
            emit(handleException(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun searchMovies(query: String): Flow<Resource<List<MovieDto>>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.searchMovies(query)
            emit(Resource.Success(response.results))
        } catch (e: Exception) {
            emit(handleException(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetailDto>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getMovieDetails(movieId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(handleException(e))
        }
    }.flowOn(Dispatchers.IO)

    private fun handleException(e: Exception): Resource.Error {
        return when (e) {
            is HttpException -> Resource.Error("Network error: ${e.code()}")
            is IOException -> Resource.Error("Check your internet connection.")
            else -> Resource.Error("Unexpected error occurred.")
        }
    }
}