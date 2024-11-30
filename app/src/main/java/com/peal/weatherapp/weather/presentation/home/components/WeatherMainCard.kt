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
import androidx.compose.ui.unit.dp
import com.peal.weatherapp.weather.presentation.home.HomeScreenAction
import com.peal.weatherapp.weather.presentation.models.WeatherUi


@Composable
fun WeatherMainCard(
    weather: WeatherUi,
    onAction: (HomeScreenAction) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            WeatherHeader(
                cityName = weather.cityName,
                date = weather.date,
                onAction = onAction
            )

            WeatherTempContent(
                iconUrl = weather.icon,
                temperature = weather.temp,
                feelsLike = weather.feelsLike
            )

            Text(
                text = weather.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            )
        }
    }
}