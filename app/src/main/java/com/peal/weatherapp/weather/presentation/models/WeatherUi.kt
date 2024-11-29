package com.peal.weatherapp.weather.presentation.models

import com.peal.weatherapp.weather.domain.models.WeatherDetails


data class WeatherUi(
    val cityName: String,
    val temp: String,
    val description: String,
    val date: String,
    val icon: String,
    val windSpeed: String,
    val humidity: String,
    val pressure: String,
)

fun WeatherDetails.toWeatherUi(): WeatherUi {
    return WeatherUi(
        cityName = cityName ?: "",
        temp = temp.toDisplayableTemp(),
        description = description ?: "",
        date = dateTime.toDisplayableDate(),
        icon = icon.toIconUri(),
        windSpeed = windSpeed ?: "",
        humidity = humidity ?: "",
        pressure = pressure ?: "",
    )
}

private fun String?.toIconUri(): String {
    return ""
}

private fun Long?.toDisplayableDate(): String {
    return ""
}

private fun Double?.toDisplayableTemp(): String {
    return ""
}