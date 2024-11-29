package com.peal.weatherapp.weather.data.mappers

import com.peal.weatherapp.weather.domain.models.WeatherDetails
import com.peal.weatherapp.weather.data.network.dto.WeatherDto


fun WeatherDto.toWeatherDetails(): WeatherDetails {
    return WeatherDetails(
        temp = mainWeatherData.temperature,
        icon = "",
        cityName = cityName,
        cityId = cityId,
        countryName = "",
        dateTime = timestamp
    )
}