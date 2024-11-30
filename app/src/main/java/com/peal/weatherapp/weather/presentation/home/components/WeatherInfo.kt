package com.peal.weatherapp.weather.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.peal.weatherapp.ui.theme.WeatherAppTheme
import com.peal.weatherapp.weather.presentation.WeatherState


@Composable
fun WeatherInfo(
    modifier: Modifier,
    weatherState: WeatherState
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                WeatherHeader(
                    cityName = weatherState.weather?.cityName ?: "",
                    date = weatherState.weather?.date ?: ""
                )

                WeatherTempContent(
                    iconUrl = weatherState.weather?.icon,
                    temperature = weatherState.weather?.temp ?: "",
                    feelsLike = weatherState.weather?.feelsLike
                )

                Text(
                    text = weatherState.weather?.description ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                )
            }
        }

        WeatherDetailsCard(
            humidity = weatherState.weather?.humidity,
            windSpeed = weatherState.weather?.windSpeed,
            pressure = weatherState.weather?.pressure,
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