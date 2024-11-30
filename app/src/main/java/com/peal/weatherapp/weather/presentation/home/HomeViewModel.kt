package com.peal.weatherapp.weather.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peal.weatherapp.core.domain.util.onError
import com.peal.weatherapp.core.domain.util.onSuccess
import com.peal.weatherapp.weather.domain.WeatherDataSource
import com.peal.weatherapp.weather.domain.location.LocationTracker
import com.peal.weatherapp.weather.presentation.SelectedCoord
import com.peal.weatherapp.weather.presentation.WeatherEvent
import com.peal.weatherapp.weather.presentation.WeatherState
import com.peal.weatherapp.weather.presentation.models.ZilaUi
import com.peal.weatherapp.weather.presentation.models.toWeatherUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherDataSource: WeatherDataSource,
    private val locationTracker: LocationTracker
) : ViewModel() {
    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState = _weatherState

    private val _weatherEvents = Channel<WeatherEvent>()
    val weatherEvents = _weatherEvents.receiveAsFlow()

    private val _locationEvents = Channel<LocationEvent>()
    val locationEvents = _locationEvents.receiveAsFlow()


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
                        _weatherState.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
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
            getCurrentWeather(_weatherState.value.selectedCoord)
        }
    }


    fun onFetchCurrentLocation() {
        viewModelScope.launch {
            val result = locationTracker.getCurrentLocation()

            result.onSuccess { location ->
                val lat = String.format("%.4f", location.latitude).toDouble()
                val lon = String.format("%.4f", location.longitude).toDouble()

                _weatherState.value = _weatherState.value.copy(
                    selectedCoord = SelectedCoord(lat = lat, lon = lon)
                )

                getCurrentWeather(_weatherState.value.selectedCoord)
            }.onFailure { error ->
                _weatherState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
                _locationEvents.send(LocationEvent.Error(error))
            }
        }
    }


}