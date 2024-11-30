package com.peal.weatherapp.weather.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.peal.weatherapp.ui.theme.WeatherAppTheme
import com.peal.weatherapp.weather.presentation.WeatherState
import com.peal.weatherapp.weather.presentation.common.LoadingState
import com.peal.weatherapp.weather.presentation.home.HomeScreenAction
import com.peal.weatherapp.weather.presentation.models.WeatherUi


@Composable
fun WeatherInfo(
    modifier: Modifier = Modifier,
    weatherState: WeatherState,
    onAction: (HomeScreenAction) -> Unit
) {
    when {
        weatherState.isLoading -> LoadingState(modifier)
        weatherState.weather == null -> ErrorState(modifier, onAction)
        else -> WeatherContent(
            modifier = modifier,
            weather = weatherState.weather,
            onAction = onAction
        )
    }
}

@Composable
private fun WeatherContent(
    modifier: Modifier = Modifier,
    weather: WeatherUi,
    onAction: (HomeScreenAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        WeatherMainCard(weather, onAction)
        WeatherDetailsCard(
            humidity = weather.humidity,
            windSpeed = weather.windSpeed,
            pressure = weather.pressure,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun WeatherInfoPreview() {
    WeatherAppTheme {
//        WeatherInfo()
    }
}