package com.warp.warpweatherapp.data.location

data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val cityName: String? = null
)