package com.peal.weatherapp.weather.presentation

import com.peal.weatherapp.core.domain.util.NetworkError

sealed interface WeatherEvent {
    data class Error(val error: NetworkError): WeatherEvent
}