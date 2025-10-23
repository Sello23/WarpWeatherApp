package com.warp.warpweatherapp.core

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.warp.warpweatherapp.data.location.LocationData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FusedLocationManager @Inject constructor(
    private val context: Context
) : LocationManager {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override val currentLocation: Flow<LocationData?> = callbackFlow {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10_000L // every 10 seconds
        ).build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    trySend(
                        LocationData(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    )
                }
            }
        }

        @SuppressLint("MissingPermission")
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            callback,
            null
        )

        awaitClose { fusedLocationClient.removeLocationUpdates(callback) }
    }.distinctUntilChanged()

    override suspend fun startLocationUpdates() { /* handled automatically when collected */
    }

    override suspend fun stopLocationUpdates() { /* handled automatically by awaitClose */
    }
}