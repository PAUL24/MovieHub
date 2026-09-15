package com.acuminx.moviehub.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acuminx.moviehub.core.network.Resource
import com.acuminx.moviehub.data.model.MovieDto
import com.acuminx.moviehub.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<MovieDto>>(emptyList())
    val movies = _movies.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private var currentPage = 1

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (_isLoading.value) return
        viewModelScope.launch {
            repository.getPopularMovies(currentPage).collect { result ->
                when (result) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Success -> {
                        _isLoading.value = false
                        _movies.value = _movies.value + result.data
                        currentPage++
                    }
                    is Resource.Error -> {
                        _isLoading.value = false
                        _error.value = result.message
                    }
                }
            }
        }
    }

    fun refresh() {
        currentPage = 1
        _movies.value = emptyList()
        _error.value = null
        loadNextPage()
    }
}