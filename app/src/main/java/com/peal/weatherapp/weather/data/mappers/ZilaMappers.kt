package com.peal.weatherapp.weather.data.mappers

import com.peal.weatherapp.weather.data.local.dto.ZilaDto
import com.peal.weatherapp.weather.domain.models.Zila


fun ZilaDto.toZila(): Zila {
    return Zila(
        id = id,
        name= name,
        country = country,
        latitude = coord.lat,
        longitude = coord.lon,
    )
}