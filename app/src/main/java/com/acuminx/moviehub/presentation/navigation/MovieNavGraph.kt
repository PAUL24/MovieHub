package com.acuminx.moviehub.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.acuminx.moviehub.presentation.screens.detail.DetailScreen
import com.acuminx.moviehub.presentation.detail.DetailViewModel
import com.acuminx.moviehub.presentation.screens.home.HomeScreen
import com.acuminx.moviehub.presentation.home.HomeViewModel
import com.acuminx.moviehub.presentation.screens.search.SearchScreen
import com.acuminx.moviehub.presentation.search.SearchViewModel

@Composable
fun MovieNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        composable<HomeRoute> {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onNavigateToDetail = { movieId -> navController.navigate(DetailRoute(movieId)) },
                onNavigateToSearch = { navController.navigate(SearchRoute) }
            )
        }

        composable<SearchRoute> {
            val viewModel: SearchViewModel = hiltViewModel()
            SearchScreen(
                viewModel = viewModel,
                onNavigateToDetail = { movieId -> navController.navigate(DetailRoute(movieId)) }
            )
        }

        composable<DetailRoute> {
            // Viewmodel fetches ID automatically via SavedStateHandle.toRoute<DetailRoute>()
            val viewModel: DetailViewModel = hiltViewModel()
            DetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}