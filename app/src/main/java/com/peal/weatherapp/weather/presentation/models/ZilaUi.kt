package com.peal.weatherapp.weather.presentation.models

import com.peal.weatherapp.weather.domain.models.Zila


data class ZilaUi (
    val id: Int,
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)


fun Zila.toZilaUi(): ZilaUi {
    return ZilaUi(
        id = id,
        name = name,
        country = country,
        latitude = latitude,
        longitude = longitude
    )
}
