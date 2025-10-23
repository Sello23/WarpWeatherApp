@file:OptIn(ExperimentalMaterial3Api::class)

package com.warp.warpweatherapp.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.warp.warpweatherapp.data.state.WeatherUiState
import com.warp.warpweatherapp.ui.WarpWeatherAppState
import com.warp.warpweatherapp.ui.components.WeatherGradientBackground
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
    val uiScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val weatherState by viewModel.weatherState.collectAsState()
    val query by viewModel.query.collectAsState()
    val location by appState.location.collectAsState()

    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(location) {
        location?.let { viewModel.updateLocation(it.latitude, it.longitude) }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        containerColor = Color.Transparent,
        topBar = {
            AnimatedContent(targetState = expanded, label = "SearchBarTransition") { isExpanded ->
                if (isExpanded) {
                    Surface(
                        tonalElevation = 4.dp,
                        shadowElevation = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        TextField(
                            value = query,
                            onValueChange = viewModel::onQueryChange,
                            placeholder = { Text("Search city…") },
                            singleLine = true,
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = MaterialTheme.colorScheme.primary
                            ),
                            keyboardActions = KeyboardActions(onSearch = {
                                if (query.isNotBlank()) {
                                    uiScope.launch {
                                        viewModel.fetchWeatherByCity(query)
                                    }
                                    focusManager.clearFocus()
                                    expanded = false
                                }
                            }),
                            trailingIcon = {
                                IconButton(onClick = {
                                    viewModel.onQueryChange("")
                                    expanded = false
                                    focusManager.clearFocus()
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Clear search"
                                    )
                                }
                            }
                        )
                    }
                    LaunchedEffect(Unit) { focusRequester.requestFocus() }
                } else {
                    Surface(
                        tonalElevation = 3.dp,
                        shadowElevation = 1.dp,
                        shape = MaterialTheme.shapes.extraLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .height(48.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = query.ifBlank { "Search City…" },
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                            )
                        }
                        Surface(
                            modifier = Modifier
                                .fillMaxSize(),
                            color = Color.Transparent,
                            tonalElevation = 0.dp,
                            shadowElevation = 0.dp,
                            onClick = { expanded = true }
                        ) {}
                    }
                }
            }
        }
    ) { innerPadding ->

        Box(modifier = Modifier.fillMaxSize()) {
            when (val state = weatherState) {
                is WeatherUiState.Loading -> Box(
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                is WeatherUiState.Success -> {
                    val condition =
                        state.data.list.firstOrNull()?.weather?.firstOrNull()?.main ?: ""
                    WeatherGradientBackground(condition = condition) {
                        WeatherMainLayout(weather = state.data, innerPadding = innerPadding)
                    }
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

                is WeatherUiState.Empty -> Box(
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Waiting for location…",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}