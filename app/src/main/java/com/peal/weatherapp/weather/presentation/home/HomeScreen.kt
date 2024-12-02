package com.peal.weatherapp.weather.presentation.home

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.peal.weatherapp.R
import com.peal.weatherapp.core.presentation.util.ObserveAsEvents
import com.peal.weatherapp.weather.domain.util.LocationError
import com.peal.weatherapp.weather.presentation.WeatherEvent
import com.peal.weatherapp.weather.presentation.WeatherState
import com.peal.weatherapp.weather.presentation.home.components.WeatherInfo


@Composable
fun HomeScreen(
    weatherState: WeatherState,
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier,
    onAction: (HomeScreenAction) -> Unit,
) {
    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.weatherEvents) { event ->
        when (event) {
            is WeatherEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    ObserveAsEvents(events = viewModel.locationEvents) { event ->
        when (event) {
            is LocationEvent.Error -> {
                when (event.error) {
                    is LocationError.PERMISSION_NOT_GRANTED -> {
                        Toast.makeText(
                            context,
                            context.getString(R.string.location_permission_is_required_please_grant_permission_in_app_settings),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is LocationError.GPS_DISABLED -> {
                        Toast.makeText(
                            context,
                            context.getString(R.string.please_enable_gps_to_get_your_current_location),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is LocationError.LOCATION_NOT_FOUND -> {
                        Toast.makeText(
                            context,
                            context.getString(R.string.unable_to_determine_your_current_location_please_try_again),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is LocationError.LOCATION_FETCH_FAILED -> {
                        Toast.makeText(
                            context,
                            context.getString(R.string.location_fetch_failed_check_your_network_and_try_again),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        Toast.makeText(
                            context,
                            context.getString(R.string.an_unexpected_location_error_occurred),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    WeatherInfo(
        modifier = modifier,
        weatherState,
        onAction
    )

}