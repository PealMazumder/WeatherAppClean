package com.peal.weatherapp.weather.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.peal.weatherapp.weather.presentation.home.HomeScreen
import com.peal.weatherapp.weather.presentation.home.HomeScreenAction
import com.peal.weatherapp.weather.presentation.home.HomeViewModel
import com.peal.weatherapp.weather.presentation.search.SearchScreen
import com.peal.weatherapp.weather.presentation.search.SearchScreenAction
import com.peal.weatherapp.weather.presentation.search.SearchViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val weatherNavigation = remember { WeatherNavigation(navController) }
    NavHost(navController = navController, startDestination = Route.HomeScreen.route) {
        composable(Route.HomeScreen.route) {
            val weatherState by homeViewModel.weatherState.collectAsStateWithLifecycle()
            HomeScreen(
                weatherState,
                modifier = modifier,
                viewModel = homeViewModel,
                onAction = { action ->
                    when (action) {
                        is HomeScreenAction.OnZilaSearchClick -> weatherNavigation.navigateToSearch()
                    }
                },
            )
        }

        composable(Route.SearchScreen.route) {
            val searchViewModel: SearchViewModel = hiltViewModel()
            val searchState by searchViewModel.searchState.collectAsStateWithLifecycle()
            SearchScreen(
                searchState,
                onAction = { action ->
                    when (action) {
                        is SearchScreenAction.OnBackAction -> {
                            action.zila?.let { zilaUi -> homeViewModel.onZilaSelected(zila = zilaUi) }
                            navController.popBackStack()
                        }
                    }
                })
        }
    }
}
