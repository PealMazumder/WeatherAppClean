package com.peal.weatherapp.weather.domain.usecase

import com.peal.weatherapp.weather.domain.repository.SuggestionRepository
import javax.inject.Inject



class GetSuggestionsUseCase @Inject constructor(
    private val repository: SuggestionRepository
) {
    suspend operator fun invoke(query: String): List<String> = repository.getSuggestions(query)
}