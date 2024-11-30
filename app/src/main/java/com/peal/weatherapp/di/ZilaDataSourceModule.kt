package com.peal.weatherapp.di

import com.peal.weatherapp.weather.data.local.AssetZilaDataSource
import com.peal.weatherapp.weather.data.local.ZilaDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class ZilaDataSourceModule {
    @Binds
    abstract fun bindZilaDataSource(zilaDataSource: AssetZilaDataSource): ZilaDataSource
}