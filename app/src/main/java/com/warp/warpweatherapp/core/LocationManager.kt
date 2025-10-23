package com.warp.warpweatherapp.core

import com.warp.warpweatherapp.data.location.LocationData
import kotlinx.coroutines.flow.Flow

interface LocationManager {
    val currentLocation: Flow<LocationData?>
    suspend fun startLocationUpdates()
    suspend fun stopLocationUpdates()
}