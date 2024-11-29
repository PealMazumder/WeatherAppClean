package com.peal.weatherapp.weather.presentation.navigation

sealed class Route(val route: String) {
    data object HomeScreen : Route("home")
}