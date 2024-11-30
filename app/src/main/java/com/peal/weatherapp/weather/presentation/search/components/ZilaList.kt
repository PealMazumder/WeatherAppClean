package com.peal.weatherapp.weather.presentation.search.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.peal.weatherapp.weather.presentation.models.ZilaUi


@Composable
fun ZilaList(
    zila: List<ZilaUi>,
    selectedZila: ZilaUi?,
    onSelectZila: (ZilaUi) -> Unit
) {
    LazyColumn {
        items(zila, key = { it.id }) { zila ->
            ZilaItemCard(
                zila = zila,
                isSelected = zila == selectedZila,
                onClick = { onSelectZila(zila) }
            )
        }
    }
}
