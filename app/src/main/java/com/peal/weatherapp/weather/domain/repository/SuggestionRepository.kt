package com.peal.weatherapp.weather.domain.repository



interface SuggestionRepository {
    suspend fun saveQuery(query: String)
    suspend fun getSuggestions(query: String): List<String>
}
