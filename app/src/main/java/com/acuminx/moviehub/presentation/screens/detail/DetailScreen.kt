package com.acuminx.moviehub.presentation.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.acuminx.moviehub.core.network.Resource
import com.acuminx.moviehub.presentation.detail.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBackClick: () -> Unit
) {
    val detailState by viewModel.detailState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (val state = detailState) {
                is Resource.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is Resource.Error -> Text(state.message, modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.error)
                is Resource.Success -> {
                    val movie = state.data
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        SubcomposeAsyncImage(
                            model = movie.backdropUrl,
                            contentDescription = movie.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f)
                        )

                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = movie.title, style = MaterialTheme.typography.headlineMedium)
                            Spacer(modifier = Modifier.height(8.dp))

                            // Genres
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(movie.genres) { genre ->
                                    AssistChip(onClick = {}, label = { Text(genre.name) })
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Overview", style = MaterialTheme.typography.titleLarge)
                            Text(text = movie.overview, style = MaterialTheme.typography.bodyMedium)

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Cast", style = MaterialTheme.typography.titleLarge)
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(movie.credits?.cast ?: emptyList()) { cast ->
                                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(100.dp)) {
                                        SubcomposeAsyncImage(
                                            model = cast.profileUrl,
                                            contentDescription = cast.name,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.size(100.dp)
                                        )
                                        Text(text = cast.name, style = MaterialTheme.typography.bodySmall, maxLines = 1)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}