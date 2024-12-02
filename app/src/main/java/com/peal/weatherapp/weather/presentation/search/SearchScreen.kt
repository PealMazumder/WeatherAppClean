package com.peal.weatherapp.weather.presentation.search

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.peal.weatherapp.R
import com.peal.weatherapp.core.domain.util.DomainError
import com.peal.weatherapp.core.presentation.util.ObserveAsEvents
import com.peal.weatherapp.weather.presentation.search.components.SearchBar
import com.peal.weatherapp.weather.presentation.search.components.ZilaList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchState: SearchState,
    viewModel: SearchViewModel = hiltViewModel(),
    onAction: (SearchScreenAction) -> Unit,
) {
    var showSuggestions by remember { mutableStateOf(true) }
    val context = LocalContext.current

    ObserveAsEvents(events = viewModel.searchEvents) { event ->
        when (event) {
            is SearchEvent.Error -> {
                val errorMessage = when (event.error) {
                    DomainError.DATA_NOT_FOUND -> context.getString(R.string.no_data_available)
                    DomainError.FILE_NOT_FOUND -> context.getString(R.string.file_could_not_be_found)
                    DomainError.PARSING_ERROR -> context.getString(R.string.error_parsing_data)
                    DomainError.UNKNOWN_ERROR -> context.getString(R.string.an_unknown_error_occurred)
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
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        TopAppBar(
            title = { Text(stringResource(R.string.search_zila)) },
            navigationIcon = {
                IconButton(onClick = {
                    onAction(SearchScreenAction.OnBackAction(searchState.selectedZila))
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        )

        SearchBar(
            query = searchState.searchQuery,
            onQueryChange = { query ->
                viewModel.onIntent(SearchIntent.QueryChanged(query))
                showSuggestions = query.isNotEmpty() && searchState.suggestions.isNotEmpty()
            },
            placeholder = stringResource(R.string.search_zila),
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when {
                    searchState.isLoading -> LoadingIndicator()
                    searchState.zila.isEmpty() -> ErrorView(DomainError.DATA_NOT_FOUND)
                    else -> ZilaList(
                        zila = searchState.zila,
                        selectedZila = searchState.selectedZila,
                        onSelectZila = { zila ->
                            viewModel.onZilaSelected(zila)
                        }
                    )
                }
            }

            if (showSuggestions && searchState.suggestions.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                        .padding(top = 8.dp)
                        .align(Alignment.TopCenter)
                ) {
                    Column {
                        searchState.suggestions.take(5).forEach { suggestion ->
                            Text(
                                text = suggestion,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.onIntent(
                                            SearchIntent.SuggestionSelected(
                                                suggestion
                                            )
                                        )
                                        showSuggestions = false
                                    }
                                    .padding(16.dp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
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
        DomainError.DATA_NOT_FOUND -> stringResource(R.string.no_zilas_found)
        DomainError.FILE_NOT_FOUND -> stringResource(R.string.data_file_is_missing)
        DomainError.PARSING_ERROR -> stringResource(R.string.error_parsing_data)
        DomainError.UNKNOWN_ERROR -> stringResource(R.string.an_unexpected_error_occurred)
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