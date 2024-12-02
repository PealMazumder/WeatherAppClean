package com.peal.weatherapp.weather.presentation.navigation

import androidx.navigation.NavHostController


class WeatherNavigation(private val navController: NavHostController) {
    fun navigateToSearch() {
        navController.navigate(Route.SearchScreen.route)
    }
}
