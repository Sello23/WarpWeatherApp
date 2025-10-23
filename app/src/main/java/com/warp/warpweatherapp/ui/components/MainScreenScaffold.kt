package com.warp.warpweatherapp.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import com.warp.warpweatherapp.data.state.WeatherUiState

@Composable
fun MainScreenScaffold(
    expanded: Boolean,
    query: String,
    weatherState: WeatherUiState,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClear: () -> Unit,
    onError: (String) -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = Color.Transparent,
        topBar = {
            AnimatedContent(targetState = expanded, label = "SearchTransition") { isExpanded ->
                if (isExpanded) {
                    SearchBarExpanded(
                        query = query,
                        onQueryChange = onQueryChange,
                        onSearch = onSearch,
                        onClear = onClear,
                        focusRequester = focusRequester
                    )
                } else {
                    SearchBarCollapsed(
                        query = query,
                        onExpand = onExpand
                    )
                }
            }
        }
    ) { innerPadding ->
        when (weatherState) {
            is WeatherUiState.Loading -> LoadingState(innerPadding)
            is WeatherUiState.Success -> SuccessState(weatherState, innerPadding)
            is WeatherUiState.Error -> ErrorState(weatherState, innerPadding, onError)
            is WeatherUiState.Empty -> EmptyState(innerPadding)
        }
    }
}

@Composable
private fun LoadingState(innerPadding: PaddingValues) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SuccessState(state: WeatherUiState.Success, innerPadding: PaddingValues) {
    val condition = state.data.list.firstOrNull()?.weather?.firstOrNull()?.main ?: ""
    WeatherGradientBackground(condition = condition) {
        WeatherMainLayout(weather = state.data, innerPadding = innerPadding)
    }
}

@Composable
private fun ErrorState(
    state: WeatherUiState.Error,
    innerPadding: PaddingValues,
    onError: (String) -> Unit
) {
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
    onError(state.message)
}

@Composable
private fun EmptyState(innerPadding: PaddingValues) {
    Box(
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