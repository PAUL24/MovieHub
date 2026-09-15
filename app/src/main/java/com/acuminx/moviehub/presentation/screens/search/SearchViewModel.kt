package com.acuminx.moviehub.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acuminx.moviehub.core.network.Resource
import com.acuminx.moviehub.data.model.MovieDto
import com.acuminx.moviehub.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val searchResults: StateFlow<Resource<List<MovieDto>>> = _searchQuery
        .debounce(300L)
        .distinctUntilChanged()
        .filter { it.isNotBlank() }
        .flatMapLatest { query -> repository.searchMovies(query) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Resource.Success(emptyList())
        )

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
}