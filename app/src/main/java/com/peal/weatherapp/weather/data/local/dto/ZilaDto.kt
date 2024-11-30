package com.peal.weatherapp.weather.data.local.dto


data class ZilaDto(
    val id: Int,
    val name: String,
    val state: String,
    val country: String,
    val coord: CoordinatesDto
)

data class CoordinatesDto(
    val lon: Double,
    val lat: Double
)
