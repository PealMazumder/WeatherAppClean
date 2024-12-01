package com.peal.weatherapp.weather.presentation.search

import com.peal.weatherapp.core.domain.util.DomainError

sealed interface SearchEvent {
    data class Error(val error: DomainError): SearchEvent
}