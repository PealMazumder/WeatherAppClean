package com.peal.weatherapp.weather.data.local

import com.peal.weatherapp.weather.data.local.dto.ZilaDto


interface ZilaDataSource {
    suspend fun getZilaList(): List<ZilaDto>
}