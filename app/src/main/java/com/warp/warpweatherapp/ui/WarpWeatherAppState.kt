package com.warp.warpweatherapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.warp.warpweatherapp.core.LocationManager
import com.warp.warpweatherapp.data.location.LocationData
import com.warp.warpweatherapp.data.util.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberWarpWeatherAppState(
    networkMonitor: NetworkMonitor,
    locationManager: LocationManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): WarpWeatherAppState {
    return remember(navController, coroutineScope, networkMonitor, locationManager) {
        WarpWeatherAppState(
            navController = navController,
            coroutineScope = coroutineScope,
            networkMonitor = networkMonitor,
            locationManager = locationManager
        )
    }
}

@Stable
class WarpWeatherAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
    locationManager: LocationManager
) {
    val isOffline: StateFlow<Boolean> =
        networkMonitor.isOnline
            .map { isOnline -> !isOnline }
            .stateIn(coroutineScope, SharingStarted.WhileSubscribed(5_000),
                false)

    val location: StateFlow<LocationData?> =
        locationManager.currentLocation
            .stateIn(coroutineScope, SharingStarted.WhileSubscribed(5_000),
                null)


}