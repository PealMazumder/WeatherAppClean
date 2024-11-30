package com.peal.weatherapp.weather.presentation.home


sealed interface LocationEvent {
    data class Error(val error: Throwable): LocationEvent
}
