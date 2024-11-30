package com.peal.weatherapp.weather.domain.util


sealed class LocationError : Exception() {
    data object PERMISSION_NOT_GRANTED : LocationError()
    data object LOCATION_NOT_FOUND : LocationError()
    data object LOCATION_FETCH_FAILED : LocationError()
    data object GPS_DISABLED : LocationError()
}