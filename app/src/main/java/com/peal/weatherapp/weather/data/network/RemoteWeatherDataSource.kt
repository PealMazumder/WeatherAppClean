package com.peal.weatherapp.weather.data.network

import com.peal.weatherapp.core.data.network.ApiService
import com.peal.weatherapp.core.data.network.safeCall
import com.peal.weatherapp.core.domain.util.NetworkError
import com.peal.weatherapp.core.domain.util.Result
import com.peal.weatherapp.core.domain.util.map
import com.peal.weatherapp.weather.data.mappers.toWeatherDetails
import com.peal.weatherapp.weather.data.network.dto.WeatherDto
import com.peal.weatherapp.weather.domain.repository.WeatherDataSource
import com.peal.weatherapp.weather.domain.models.WeatherDetails
import javax.inject.Inject


class RemoteWeatherDataSource @Inject constructor(
    private val apiService: ApiService
): WeatherDataSource {

    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Result<WeatherDetails, NetworkError> {
        return safeCall<WeatherDto> {
            apiService.getCurrentWeather(
                latitude,
                longitude
            )
        }.map { weatherResponse ->
            weatherResponse.toWeatherDetails()
        }
    }
}