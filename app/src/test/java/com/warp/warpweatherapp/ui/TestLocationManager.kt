package com.warp.warpweatherapp.ui

import com.warp.warpweatherapp.core.LocationManager
import com.warp.warpweatherapp.data.location.LocationData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TestLocationManager : LocationManager {

    private val _currentLocation = MutableStateFlow<LocationData?>(null)
    override val currentLocation: Flow<LocationData?> = _currentLocation.asStateFlow()

    private var isUpdating = false

    override suspend fun startLocationUpdates() {
        isUpdating = true
    }

    override suspend fun stopLocationUpdates() {
        isUpdating = false
    }

    fun emitLocation(latitude: Double, longitude: Double) {
        if (isUpdating) {
            _currentLocation.value = LocationData(latitude = latitude, longitude = longitude)
        } else {
            error("Location updates not started. Call startLocationUpdates() first.")
        }
    }

    fun clearLocation() {
        _currentLocation.value = null
    }
}