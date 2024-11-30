package com.peal.weatherapp.weather.presentation.search

import com.peal.weatherapp.core.domain.util.DomainError

sealed interface ZilaEvent {
    data class Error(val error: DomainError): ZilaEvent
}