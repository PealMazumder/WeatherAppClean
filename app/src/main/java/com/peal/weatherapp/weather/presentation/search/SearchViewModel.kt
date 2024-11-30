package com.peal.weatherapp.weather.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peal.weatherapp.core.domain.util.onError
import com.peal.weatherapp.core.domain.util.onSuccess
import com.peal.weatherapp.weather.domain.usecase.ZilaListUseCase
import com.peal.weatherapp.weather.presentation.models.ZilaUi
import com.peal.weatherapp.weather.presentation.models.toZilaUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val zilaListUseCase: ZilaListUseCase
) : ViewModel() {

    private val _zilaListState = MutableStateFlow(ZilaState())
    val zilaListState = _zilaListState
        .onStart { getZila() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ZilaState()
        )

    private var originalZilaList: List<ZilaUi> = emptyList()

    private val _zilaEvents = Channel<ZilaEvent>()
    val zilaEvents = _zilaEvents.receiveAsFlow()

    private fun getZila() {
        viewModelScope.launch {
            _zilaListState.update {
                it.copy(
                    isLoading = true
                )
            }

            zilaListUseCase
                .getZilaList()
                .onSuccess { zilaList ->
                    val uiList = zilaList.map { it.toZilaUi() }
                    _zilaListState.update {
                        it.copy(
                            isLoading = false,
                            zila = uiList
                        )
                    }
                    originalZilaList = uiList
                }
                .onError { error ->
                    _zilaListState.update { it.copy(isLoading = false) }
                    _zilaEvents.send(ZilaEvent.Error(error))
                }
        }
    }

    fun searchZila(query: String) {
        val filteredList = if (query.isEmpty()) {
            originalZilaList
        } else {
            originalZilaList.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.country.contains(query, ignoreCase = true)
            }
        }

        _zilaListState.update {
            it.copy(
                zila = filteredList
            )
        }
    }


    fun onZilaSelected(zila: ZilaUi) {
        _zilaListState.value = _zilaListState.value.copy(selectedZila = zila)
    }

}