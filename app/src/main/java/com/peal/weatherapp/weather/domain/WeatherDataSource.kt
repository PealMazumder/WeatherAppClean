package com.peal.weatherapp.weather.domain

import com.peal.weatherapp.core.domain.util.NetworkError
import com.peal.weatherapp.core.domain.util.Result
import com.peal.weatherapp.weather.domain.models.WeatherDetails

interface WeatherDataSource {
    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Result<WeatherDetails, NetworkError>
}