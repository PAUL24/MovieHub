package com.acuminx.moviehub.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object SearchRoute

@Serializable
data class DetailRoute(val movieId: Int)