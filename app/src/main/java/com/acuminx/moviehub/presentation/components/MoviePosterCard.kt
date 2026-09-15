package com.acuminx.moviehub.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.acuminx.moviehub.data.model.MovieDto

@Composable
fun MoviePosterCard(
    movie: MovieDto,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.67f)
            .clickable { onClick(movie.id) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        SubcomposeAsyncImage(
            model = movie.posterUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = { Box(contentAlignment = Alignment.Center) { CircularProgressIndicator() } },
            error = { Box(contentAlignment = Alignment.Center) { Text("No Image") } }
        )
    }
}

@Preview
@Composable
fun PreviewMoviePosterCard() {
    MoviePosterCard(
        movie = MovieDto(
            id = 1,
            title = "Mock Movie",
            overview = "Overview",
            posterPath = null,
            backdropPath = null,
            voteAverage = 0.0
        ),
        onClick = {}
    )
}