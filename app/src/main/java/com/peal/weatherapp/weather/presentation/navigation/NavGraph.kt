package com.peal.weatherapp.weather.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.peal.weatherapp.weather.presentation.home.HomeScreen
import com.peal.weatherapp.weather.presentation.home.HomeScreenAction
import com.peal.weatherapp.weather.presentation.home.HomeViewModel
import com.peal.weatherapp.weather.presentation.search.SearchScreen
import com.peal.weatherapp.weather.presentation.search.SearchScreenAction

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier,  homeViewModel: HomeViewModel = hiltViewModel()) {
    NavHost(navController = navController, startDestination = Route.HomeScreen.route) {
        composable(Route.HomeScreen.route) {
            HomeScreen(
                modifier = modifier,
                viewModel = homeViewModel,
                onAction = { action ->
                    when (action) {
                        is HomeScreenAction.OnZilaSearchClick -> navigateToSearch(
                            navController
                        )
                    }
                }
            )
        }

        composable(Route.SearchScreen.route) {
            SearchScreen(navController = navController, onAction = { action ->
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

fun navigateToSearch(navController: NavController) {
    navController.navigate(Route.SearchScreen.route)
}
