package com.peal.weatherapp.weather.presentation.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.peal.weatherapp.R


@Composable
fun WeatherDetailsCard(
    modifier: Modifier = Modifier,
    humidity: String?,
    windSpeed: String?,
    pressure: String?
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WeatherDetailItem(
                icon = R.drawable.humidity,
                label = stringResource(R.string.humidity),
                value = "${humidity ?: "N/A"}%",
                modifier = Modifier.weight(1f)
            )

            WeatherDetailItem(
                icon = R.drawable.wind_speed,
                label = stringResource(R.string.wind_speed),
                value = windSpeed ?: "N/A",
                modifier = Modifier.weight(1f)
            )

            WeatherDetailItem(
                icon = R.drawable.pressure,
                label = stringResource(R.string.pressure),
                value = pressure ?: "N/A",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun WeatherDetailItem(
    @DrawableRes
    icon: Int,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.size(48.dp),
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}