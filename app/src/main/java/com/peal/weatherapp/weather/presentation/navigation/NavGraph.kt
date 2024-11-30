package com.peal.weatherapp.weather.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.peal.weatherapp.weather.presentation.home.HomeScreen
import com.peal.weatherapp.weather.presentation.search.SearchScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = Route.HomeScreen.route) {
        composable(Route.HomeScreen.route) {
            HomeScreen(
                modifier = modifier
            )
        }

        composable(Route.SearchScreen.route) {
            SearchScreen(
                navController = navController,
            )
        }
    }
}