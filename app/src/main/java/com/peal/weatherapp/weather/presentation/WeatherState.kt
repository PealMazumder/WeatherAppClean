package com.peal.weatherapp.weather.presentation

import androidx.compose.runtime.Immutable
import com.peal.weatherapp.weather.presentation.models.WeatherUi

@Immutable
data class WeatherState(
    val isLoading: Boolean = false,
    val weather: WeatherUi? = null,
    val selectedCoord: SelectedCoord? = null,
)

@Immutable
data class SelectedCoord(
    val lon: Double,
    val lat: Double
)
