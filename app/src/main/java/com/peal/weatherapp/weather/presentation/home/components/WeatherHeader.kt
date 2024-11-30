package com.peal.weatherapp.weather.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.peal.weatherapp.R
import com.peal.weatherapp.weather.presentation.home.HomeScreenAction


@Composable
fun WeatherHeader(
    cityName: String,
    date: String,
    onAction: (HomeScreenAction) -> Unit
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier.clickable(
                onClick = {
                    onAction(HomeScreenAction.OnZilaSearchClick)
                }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = cityName,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = stringResource(R.string.search),
                modifier = Modifier.size(24.dp),
            )
        }

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
