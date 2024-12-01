package com.peal.weatherapp.weather.data.repository

import com.peal.weatherapp.weather.data.local.SuggestionEntity
import com.peal.weatherapp.weather.data.local.SuggestionsDao
import com.peal.weatherapp.weather.domain.repository.SuggestionRepository
import javax.inject.Inject

class SuggestionRepositoryImpl @Inject constructor(
    private val suggestionsDao: SuggestionsDao
) : SuggestionRepository {

    override suspend fun saveQuery(query: String) {
        suggestionsDao.insertSuggestion(SuggestionEntity(query = query))
    }

    override suspend fun getSuggestions(query: String): List<String> {
        return suggestionsDao.getSuggestions(query)
    }
}