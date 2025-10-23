package com.warp.warpweatherapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.warp.warpweatherapp.data.state.WeatherUiState
import com.warp.warpweatherapp.ui.WarpWeatherAppState
import com.warp.warpweatherapp.ui.components.WeatherMainLayout
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    appState: WarpWeatherAppState,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val context = LocalContext.current
    val coroutineScope = appState.coroutineScope

    val weatherState by viewModel.weatherState.collectAsState()
    val location by appState.location.collectAsState()

    LaunchedEffect(location) {
        location?.let {
            viewModel.fetchWeatherByCoordinates(it.latitude, it.longitude)
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        containerColor = Color.Transparent
    ) { innerPadding ->

        when (val state = weatherState) {

            is WeatherUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is WeatherUiState.Success -> {
                WeatherMainLayout(
                    weather = state.data,
                    innerPadding = innerPadding
                )
            }

            is WeatherUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                LaunchedEffect(state.message) {
                    coroutineScope.launch {
                        Toast.makeText(
                            context,
                            "Error: ${state.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            is WeatherUiState.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Waiting for location...",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}