@file:OptIn(ExperimentalMaterial3Api::class)

package com.warp.warpweatherapp.ui.screens

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.warp.warpweatherapp.ui.WarpWeatherAppState
import com.warp.warpweatherapp.ui.components.MainScreenScaffold
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    appState: WarpWeatherAppState,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val weatherState by viewModel.weatherState.collectAsState()
    val query by viewModel.query.collectAsState()
    val location by appState.location.collectAsState()

    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(location) {
        location?.let { (lat, lon) ->
            viewModel.updateLocation(lat, lon)
        }
    }

    MainScreenScaffold(
        expanded = expanded,
        query = query,
        weatherState = weatherState,
        onExpand = { expanded = true },
        onCollapse = { expanded = false },
        onQueryChange = viewModel::onQueryChange,
        onSearch = {
            if (query.isNotBlank()) {
                coroutineScope.launch { viewModel.fetchWeatherByCity(query) }
                focusManager.clearFocus()
                expanded = false
            }
        },
        onClear = {
            viewModel.onQueryChange("")
            expanded = false
            focusManager.clearFocus()
        },
        onError = { message ->
            coroutineScope.launch {
                Toast.makeText(context, "Error: $message", Toast.LENGTH_LONG).show()
            }
        },
        focusRequester = focusRequester,
        focusManager = focusManager,
        snackBarHostState = snackBarHostState,
        modifier = modifier
    )
}