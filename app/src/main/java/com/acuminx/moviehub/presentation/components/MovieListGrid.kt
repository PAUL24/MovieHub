package com.acuminx.moviehub.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.acuminx.moviehub.data.model.MovieDto
import com.acuminx.moviehub.presentation.components.MoviePosterCard

@Composable
fun MovieListGrid(
    movies: List<MovieDto>,
    isLoading: Boolean,
    onLoadMore: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    val listState = rememberLazyGridState()

    // Infinite scroll trigger
    val shouldLoadMore by remember {
        derivedStateOf {
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            totalItemsCount > 0 && lastVisibleItemIndex >= (totalItemsCount - 4)
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && !isLoading) {
            onLoadMore()
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = listState,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies.size, key = { index -> movies[index].id }) { index ->
            MoviePosterCard(
                movie = movies[index],
                onClick = onMovieClick
            )
        }
        if (isLoading) {
            item(span = { GridItemSpan(2) }) {
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}