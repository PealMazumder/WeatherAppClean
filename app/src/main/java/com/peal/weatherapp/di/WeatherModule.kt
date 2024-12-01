package com.peal.weatherapp.di

import com.peal.weatherapp.weather.domain.repository.WeatherDataSource
import com.peal.weatherapp.weather.domain.usecase.GetCurrentWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideGetCurrentWeatherUseCase(weatherDataSource: WeatherDataSource): GetCurrentWeatherUseCase {
        return GetCurrentWeatherUseCase(weatherDataSource)
    }
}