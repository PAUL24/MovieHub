package com.acuminx.moviehub.presentation.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.acuminx.moviehub.core.network.Resource
import com.acuminx.moviehub.presentation.components.MoviePosterCard
import com.acuminx.moviehub.presentation.components.SearchBar
import com.acuminx.moviehub.presentation.search.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onNavigateToDetail: (Int) -> Unit
) {
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchState by viewModel.searchResults.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = query,
            onQueryChange = viewModel::onSearchQueryChange,
            modifier = Modifier.padding(16.dp)
        )

        when (val state = searchState) {
            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                }
            }
            is Resource.Success -> {
                if (state.data.isEmpty() && query.isNotBlank()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No movies found.")
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.data.size) { index ->
                            MoviePosterCard(movie = state.data[index], onClick = onNavigateToDetail)
                        }
                    }
                }
            }
        }
    }
}