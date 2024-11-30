package com.peal.weatherapp.weather.domain.models

data class WeatherDetails(
    var cityName: String? = null,
    var temp: Double? = null,
    var feelsLike: Double? = null,
    val description: String? = null,
    var icon: String? = null,
    var cityId: Int? = null,
    var countryName: String? = null,
    var dateTime: Long? = null,
    val windSpeed: Double? = null,
    val humidity: Int? = null,
    val pressure: Int? = null,
)
