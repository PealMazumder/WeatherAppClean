package com.peal.weatherapp.weather.presentation.search


sealed class SearchIntent {
    data class QueryChanged(val query: String) : SearchIntent()
    data class SuggestionSelected(val suggestion: String) : SearchIntent()
}
