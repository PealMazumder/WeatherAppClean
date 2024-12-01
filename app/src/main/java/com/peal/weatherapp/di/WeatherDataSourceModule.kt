package com.peal.weatherapp.di

import com.peal.weatherapp.weather.data.network.RemoteWeatherDataSource
import com.peal.weatherapp.weather.domain.repository.WeatherDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class WeatherDataSourceModule {
    @Binds
    abstract fun bindWeatherDataSource(dataSource: RemoteWeatherDataSource): WeatherDataSource
}