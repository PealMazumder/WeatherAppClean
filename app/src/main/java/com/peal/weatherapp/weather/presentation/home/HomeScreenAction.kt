package com.peal.weatherapp.weather.presentation.home


sealed interface HomeScreenAction {
    data object OnZilaSearchClick : HomeScreenAction
}