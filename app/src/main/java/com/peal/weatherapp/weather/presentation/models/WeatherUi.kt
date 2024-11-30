package com.peal.weatherapp.weather.presentation.models

import com.peal.weatherapp.weather.domain.models.WeatherDetails
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt


data class WeatherUi(
    val cityName: String,
    val temp: String,
    val feelsLike: String,
    val description: String,
    val date: String,
    val icon: String,
    val windSpeed: String,
    val humidity: String,
    val pressure: String,
)

fun WeatherDetails.toWeatherUi(): WeatherUi {
    return WeatherUi(
        cityName = cityName ?: "Unknown Location",
        temp = temp.toDisplayableTemp(),
        feelsLike = feelsLike.toDisplayableTemp(),
        description = (description ?: "No description").replaceFirstChar { it.uppercase() },
        date = dateTime.toDisplayableDate(),
        icon = icon.toIconUri(),
        windSpeed = windSpeed?.toWindSpeedDisplay() ?: "N/A",
        humidity = humidity?.toHumidityDisplay() ?: "N/A",
        pressure = pressure?.toPressureDisplay() ?: "N/A"
    )
}

private fun String?.toIconUri(): String {
    return "https://openweathermap.org/img/wn/$this@2x.png"
}


private fun Long?.toDisplayableDate(): String {
    return try {
        this?.let { timestamp ->
            SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())
                .format(Date(timestamp * 1000))
        } ?: "Unknown Date"
    } catch (e: Exception) {
        "Invalid Date"
    }
}

private fun Double?.toDisplayableTemp(): String {
    return try {
        this?.let { temp ->
            val celsius = (temp - 273.15).roundToInt()
            "$celsius°C"
        } ?: "N/A"
    } catch (e: Exception) {
        "N/A"
    }
}

private fun Double.toWindSpeedDisplay(): String {
    return "${String.format("%.1f", this)} km/h"
}

private fun Int.toHumidityDisplay(): String {
    return "$this"
}

private fun Int.toPressureDisplay(): String {
    return "$this hPa"
}
