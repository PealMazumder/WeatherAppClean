package com.peal.weatherapp.weather.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peal.weatherapp.core.domain.util.onError
import com.peal.weatherapp.core.domain.util.onSuccess
import com.peal.weatherapp.weather.domain.usecase.GetSuggestionsUseCase
import com.peal.weatherapp.weather.domain.usecase.SaveQueryUseCase
import com.peal.weatherapp.weather.domain.usecase.ZilaListUseCase
import com.peal.weatherapp.weather.presentation.models.ZilaUi
import com.peal.weatherapp.weather.presentation.models.toZilaUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val zilaListUseCase: ZilaListUseCase,
    private val getSuggestionsUseCase: GetSuggestionsUseCase,
    private val saveQueryUseCase: SaveQueryUseCase,
) : ViewModel() {

    private val _searchState = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState

    private val _searchEvents = Channel<SearchEvent>(Channel.BUFFERED)
    val searchEvents = _searchEvents.receiveAsFlow()

    private var originalZilaList: List<ZilaUi> = emptyList()
    private var searchJob: Job? = null

    init {
        fetchZilaList()
    }

    private fun fetchZilaList() {
        viewModelScope.launch {
            _searchState.update { it.copy(isLoading = true) }
            try {
                zilaListUseCase.getZilaList()
                    .onSuccess { zilaList ->
                        val uiList = zilaList.map { it.toZilaUi() }
                        originalZilaList = uiList
                        _searchState.update {
                            it.copy(
                                isLoading = false,
                                zila = uiList
                            )
                        }
                    }
                    .onError { error ->
                        _searchState.update { it.copy(isLoading = false) }
                        _searchEvents.send(SearchEvent.Error(error))
                    }
            } catch (e: Exception) {
                _searchState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.QueryChanged -> {
                _searchState.update { it.copy(searchQuery = intent.query) }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(300)
                    updateSuggestions(intent.query)
                    filterZilaList(intent.query)
                }
            }

            is SearchIntent.SuggestionSelected -> {
                _searchState.update { it.copy(searchQuery = intent.suggestion) }
                viewModelScope.launch {
                    filterZilaList(intent.suggestion)
                }
            }
        }
    }

    private suspend fun updateSuggestions(query: String) {
        try {
            _searchState.update {
                it.copy(
                    suggestions = if (query.isNotEmpty()) {
                        getSuggestionsUseCase.invoke(query)
                    } else {
                        emptyList()
                    }
                )
            }
        } catch (e: Exception) {
            e.stackTraceToString()
        }
    }

    private suspend fun filterZilaList(query: String) {
        val filteredList = if (query.isEmpty()) {
            originalZilaList
        } else {
            originalZilaList.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.country.contains(query, ignoreCase = true)
            }
        }

        _searchState.update { it.copy(zila = filteredList) }

        if (query.isNotEmpty()) {
            try {
                saveQueryUseCase.invoke(query)
            } catch (e: Exception) {
                e.stackTraceToString()
            }
        }
    }

    fun onZilaSelected(zila: ZilaUi) {
        _searchState.update { it.copy(selectedZila = zila) }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}