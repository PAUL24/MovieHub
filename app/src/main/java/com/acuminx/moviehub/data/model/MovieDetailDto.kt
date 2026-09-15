package com.acuminx.moviehub.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double = 0.0,
    val genres: List<GenreDto> = emptyList(),
    val credits: CreditsDto? = null
) {
    val backdropUrl: String get() = "https://image.tmdb.org/t/p/w1280$backdropPath"
}

@Serializable
data class GenreDto(val id: Int, val name: String)

@Serializable
data class CreditsDto(val cast: List<CastDto> = emptyList())

@Serializable
data class CastDto(
    val id: Int,
    val name: String,
    val character: String,
    @SerialName("profile_path") val profilePath: String? = null
) {
    val profileUrl: String get() = "https://image.tmdb.org/t/p/w500$profilePath"
}