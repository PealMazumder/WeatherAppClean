package com.peal.weatherapp.weather.data.mappers

import com.peal.weatherapp.weather.domain.models.WeatherDetails
import com.peal.weatherapp.weather.data.network.dto.WeatherDto


fun WeatherDto.toWeatherDetails(): WeatherDetails {
    return WeatherDetails(
        temp = mainWeatherData.temperature,
        feelsLike = mainWeatherData.feelsLikeTemperature,
        icon = weatherConditions.firstOrNull()?.iconCode,
        description = weatherConditions.firstOrNull()?.description,
        cityName = cityName,
        cityId = cityId,
        countryName = systemData.countryCode,
        dateTime = timestamp,
        windSpeed = windData.windSpeed,
        pressure = mainWeatherData.atmosphericPressure,
        humidity = mainWeatherData.humidityPercentage
    )
}