package com.peal.weatherapp.core.data.network

import com.peal.weatherapp.core.util.APIServiceConst
import com.peal.weatherapp.weather.data.network.dto.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(APIServiceConst.CURRENT_WEATHER)
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = APIServiceConst.API_KEY,
    ): Response<WeatherDto>
}