package com.peal.weatherapp.weather.data.network.dto

import com.google.gson.annotations.SerializedName


data class WeatherDto(
    @SerializedName("coord")
    val coordinates: Coord,

    @SerializedName("weather")
    val weatherConditions: List<Weather>,

    @SerializedName("base")
    val base: String,

    @SerializedName("main")
    val mainWeatherData: Main,

    @SerializedName("visibility")
    val visibility: Int,

    @SerializedName("wind")
    val windData: Wind,

    @SerializedName("rain")
    val rainData: Rain? = null,

    @SerializedName("clouds")
    val cloudData: Clouds,

    @SerializedName("dt")
    val timestamp: Long,

    @SerializedName("sys")
    val systemData: Sys,

    @SerializedName("timezone")
    val timezone: Int,

    @SerializedName("id")
    val cityId: Int,

    @SerializedName("name")
    val cityName: String,

    @SerializedName("cod")
    val responseCode: Int
)

data class Coord(
    @SerializedName("lon")
    val longitude: Double,

    @SerializedName("lat")
    val latitude: Double
)

data class Weather(
    @SerializedName("id")
    val conditionId: Int,

    @SerializedName("main")
    val mainCondition: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val iconCode: String
)

data class Main(
    @SerializedName("temp")
    val temperature: Double,

    @SerializedName("feels_like")
    val feelsLikeTemperature: Double,

    @SerializedName("temp_min")
    val minimumTemperature: Double,

    @SerializedName("temp_max")
    val maximumTemperature: Double,

    @SerializedName("pressure")
    val atmosphericPressure: Int,

    @SerializedName("humidity")
    val humidityPercentage: Int,

    @SerializedName("sea_level")
    val seaLevelPressure: Int? = null,

    @SerializedName("grnd_level")
    val groundLevelPressure: Int? = null
)

data class Wind(
    @SerializedName("speed")
    val windSpeed: Double,

    @SerializedName("deg")
    val windDirection: Int,

    @SerializedName("gust")
    val windGust: Double? = null
)

data class Rain(
    @SerializedName("1h")
    val oneHourRainfall: Double? = null,

    @SerializedName("3h")
    val threeHourRainfall: Double? = null
)

data class Clouds(
    @SerializedName("all")
    val cloudCoveragePercentage: Int
)

data class Sys(
    @SerializedName("type")
    val systemType: Int? = null,

    @SerializedName("id")
    val systemId: Int? = null,

    @SerializedName("country")
    val countryCode: String,

    @SerializedName("sunrise")
    val sunriseTimestamp: Long,

    @SerializedName("sunset")
    val sunsetTimestamp: Long
)

