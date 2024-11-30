package com.peal.weatherapp.weather.presentation.search

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peal.weatherapp.core.domain.util.DomainError
import com.peal.weatherapp.core.presentation.util.ObserveAsEvents
import com.peal.weatherapp.weather.presentation.search.components.SearchBar
import com.peal.weatherapp.weather.presentation.search.components.ZilaList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {
    val zilaState by viewModel.zilaListState.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current

    ObserveAsEvents(events = viewModel.zilaEvents) { event ->
        when (event) {
            is ZilaEvent.Error -> {
                val errorMessage = when (event.error) {
                    DomainError.DATA_NOT_FOUND -> "No data available"
                    DomainError.FILE_NOT_FOUND -> "File could not be found"
                    DomainError.PARSING_ERROR -> "Error parsing data"
                    DomainError.UNKNOWN_ERROR -> "An unknown error occurred"
                }
                Toast.makeText(
                    context,
                    errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        TopAppBar(
            title = { Text("Search Zila") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        SearchBar(
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
                viewModel.searchZila(it)
            },
            placeholder = "Search Zila"
        )

        when {
            zilaState.isLoading -> LoadingIndicator()
            zilaState.zila.isEmpty() -> ErrorView(DomainError.DATA_NOT_FOUND)
            else -> ZilaList(
                zila = zilaState.zila,
                selectedZila = zilaState.selectedZila,
                onSelectZila = { zila -> viewModel.onZilaSelected(zila) }
            )
        }
    }
}


@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(error: DomainError) {
    val errorMessage = when (error) {
        DomainError.DATA_NOT_FOUND -> "No Zilas found"
        DomainError.FILE_NOT_FOUND -> "Data file is missing"
        DomainError.PARSING_ERROR -> "Error parsing data"
        DomainError.UNKNOWN_ERROR -> "An unexpected error occurred"
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error
        )
    }
}