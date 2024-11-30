package com.peal.weatherapp.weather.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun WeatherHeader(
    cityName: String,
    date: String
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .wrapContentHeight()
    ) {
        Text(
            text = cityName,
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
        )

        CustomHorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            height = 2.dp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Text(
            text = date,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
        )
    }
}
