package com.peal.weatherapp.weather.presentation.search

import androidx.compose.runtime.Immutable
import com.peal.weatherapp.weather.presentation.models.ZilaUi

@Immutable
data class SearchState(
    val isLoading: Boolean = false,
    val zila: List<ZilaUi> = emptyList(),
    val selectedZila: ZilaUi? = null,
    val searchQuery: String = "",
    val suggestions: List<String> = emptyList(),
)