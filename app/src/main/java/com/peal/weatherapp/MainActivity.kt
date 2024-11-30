package com.peal.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import android.Manifest
import android.util.Log
import com.peal.weatherapp.ui.theme.WeatherAppTheme
import com.peal.weatherapp.weather.presentation.home.HomeViewModel
import com.peal.weatherapp.weather.presentation.navigation.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.onFetchCurrentLocation()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
        setContent {
            WeatherAppTheme {
                WeatherApp(homeViewModel= viewModel)
            }
        }
    }
}

@Composable
fun WeatherApp(
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            NavGraph(
                navController,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(
                        start = innerPadding.calculateStartPadding(LocalLayoutDirection.current) + 16.dp,
                        top = innerPadding.calculateTopPadding() + 20.dp,
                        end = innerPadding.calculateEndPadding(LocalLayoutDirection.current) + 16.dp,
                        bottom = innerPadding.calculateBottomPadding() + 10.dp
                    ),
                homeViewModel,
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun WeatherAppPreview() {

}