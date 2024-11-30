package com.peal.weatherapp.weather.presentation.search

import com.peal.weatherapp.weather.presentation.models.ZilaUi


sealed interface SearchScreenAction {
    data class OnBackAction(val zila: ZilaUi?) : SearchScreenAction
}