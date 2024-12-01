package com.peal.weatherapp.weather.domain.usecase

import com.peal.weatherapp.core.domain.util.NetworkError
import com.peal.weatherapp.core.domain.util.Result
import com.peal.weatherapp.weather.domain.models.WeatherDetails
import com.peal.weatherapp.weather.domain.repository.WeatherDataSource
import javax.inject.Inject


class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherDataSource: WeatherDataSource
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): Result<WeatherDetails, NetworkError> {
        return weatherDataSource.getCurrentWeather(latitude, longitude)
    }
}