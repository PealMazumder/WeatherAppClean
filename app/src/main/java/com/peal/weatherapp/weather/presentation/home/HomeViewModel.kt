package com.peal.weatherapp.weather.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peal.weatherapp.core.domain.util.onError
import com.peal.weatherapp.core.domain.util.onSuccess
import com.peal.weatherapp.weather.domain.WeatherDataSource
import com.peal.weatherapp.weather.presentation.SelectedCoord
import com.peal.weatherapp.weather.presentation.WeatherEvent
import com.peal.weatherapp.weather.presentation.WeatherState
import com.peal.weatherapp.weather.presentation.models.ZilaUi
import com.peal.weatherapp.weather.presentation.models.toWeatherUi
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
class HomeViewModel @Inject constructor(
    private val weatherDataSource: WeatherDataSource
) : ViewModel() {
    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState = _weatherState
        .onStart { getCurrentWeather(_weatherState.value.selectedCoord) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            WeatherState()
        )

    private val _weatherEvents = Channel<WeatherEvent>()
    val weatherEvents = _weatherEvents.receiveAsFlow()


    private fun getCurrentWeather(selectedCoord: SelectedCoord?) {
        viewModelScope.launch {
            _weatherState.update {
                it.copy(
                    isLoading = true
                )
            }

            selectedCoord?.let { coord ->
                weatherDataSource
                    .getCurrentWeather(
                        coord.lat,
                        coord.lon,
                    )
                    .onSuccess { weather ->
                        _weatherState.update {
                            it.copy(
                                isLoading = false,
                                weather = weather.toWeatherUi()
                            )
                        }
                    }
                    .onError { error ->
                        _weatherState.update { it.copy(isLoading = false) }
                        _weatherEvents.send(WeatherEvent.Error(error))
                    }
            }
        }
    }


    fun onZilaSelected(zila: ZilaUi) {
        if (_weatherState.value.selectedCoord?.lat != zila.latitude && _weatherState.value.selectedCoord?.lon != zila.longitude) {
            _weatherState.value = _weatherState.value.copy(
                selectedCoord = SelectedCoord(
                    lat = zila.latitude,
                    lon = zila.longitude
                )
            )
        }
    }
}