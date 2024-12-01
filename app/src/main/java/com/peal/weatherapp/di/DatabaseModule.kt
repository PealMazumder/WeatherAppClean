package com.peal.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.peal.weatherapp.weather.data.local.SuggestionsDao
import com.peal.weatherapp.weather.data.local.WeatherDatabase
import com.peal.weatherapp.weather.data.util.DataBaseConfig.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            DATABASE_NAME,
        ).build()
    }

    @Provides
    fun provideSuggestionsDao(database: WeatherDatabase): SuggestionsDao {
        return database.suggestionsDao()
    }
}