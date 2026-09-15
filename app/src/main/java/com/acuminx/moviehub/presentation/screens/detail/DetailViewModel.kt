package com.acuminx.moviehub.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.acuminx.moviehub.core.network.Resource
import com.acuminx.moviehub.data.model.MovieDetailDto
import com.acuminx.moviehub.domain.repository.MovieRepository
import com.acuminx.moviehub.presentation.navigation.DetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _detailState = MutableStateFlow<Resource<MovieDetailDto>>(Resource.Loading)
    val detailState = _detailState.asStateFlow()

    init {
        // Automatically extract the type-safe argument injected by Nav 2.8.0
        val movieId = savedStateHandle.toRoute<DetailRoute>().movieId
        fetchMovieDetails(movieId)
    }

    private fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            repository.getMovieDetails(movieId).collect { result ->
                _detailState.value = result
            }
        }
    }
}