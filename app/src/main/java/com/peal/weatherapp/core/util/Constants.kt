package com.peal.weatherapp.core.util

import com.peal.weatherapp.BuildConfig

object NetworkConfig {
    const val BASE_URL = BuildConfig.BASE_URL
    const val TIMEOUT = 30L
}

object APIServiceConst {
    const val API_KEY: String = BuildConfig.API_KEY
    const val CURRENT_WEATHER: String = "weather"
}