package com.peal.weatherapp.di

import com.peal.weatherapp.weather.data.local.SuggestionsDao
import com.peal.weatherapp.weather.data.repository.SuggestionRepositoryImpl
import com.peal.weatherapp.weather.domain.repository.SuggestionRepository
import com.peal.weatherapp.weather.domain.usecase.GetSuggestionsUseCase
import com.peal.weatherapp.weather.domain.usecase.SaveQueryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object SuggestionModule {

    @Provides
    fun provideSuggestionRepository(dao: SuggestionsDao): SuggestionRepository {
        return SuggestionRepositoryImpl(dao)
    }

    @Provides
    fun provideSaveQueryUseCase(repository: SuggestionRepository): SaveQueryUseCase {
        return SaveQueryUseCase(repository)
    }

    @Provides
    fun provideGetSuggestionsUseCase(repository: SuggestionRepository): GetSuggestionsUseCase {
        return GetSuggestionsUseCase(repository)
    }
}