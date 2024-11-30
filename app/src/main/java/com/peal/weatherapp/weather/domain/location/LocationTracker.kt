package com.peal.weatherapp.weather.domain.location
import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Result<Location>
}