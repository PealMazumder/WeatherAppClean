package com.peal.weatherapp.weather.presentation.home

import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peal.weatherapp.core.presentation.util.ObserveAsEvents
import com.peal.weatherapp.weather.presentation.WeatherEvent


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier
) {
    val weatherState by viewModel.weatherState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.weatherEvents) { event ->
        when(event) {
            is WeatherEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Text(modifier = modifier, text = weatherState.weather?.cityName ?: "")

}