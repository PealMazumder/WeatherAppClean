package com.peal.weatherapp.weather.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage


@Composable
fun WeatherTempContent(
    iconUrl: String?,
    temperature: String,
    feelsLike: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        WeatherIcon(
            iconUrl = iconUrl,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.medium),
            tint = MaterialTheme.colorScheme.onBackground
        )

        TemperatureDetails(
            temperature = temperature,
            feelsLike = feelsLike,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
    }
}

@Composable
private fun WeatherIcon(
    iconUrl: String?,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    AsyncImage(
        model = iconUrl,
        contentDescription = "Weather icon",
        modifier = modifier,
        contentScale = ContentScale.Fit,
        colorFilter = if (tint != Color.Unspecified) {
            ColorFilter.tint(tint)
        } else {
            null
        }
    )
}

@Composable
private fun TemperatureDetails(
    temperature: String,
    feelsLike: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = temperature,
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = "Feels like ${feelsLike ?: "N/A"}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}