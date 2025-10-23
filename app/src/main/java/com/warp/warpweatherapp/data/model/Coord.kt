package com.warp.warpweatherapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coord(
    @SerialName("lon")
    val lon: Double = 0.0,

    @SerialName("lat")
    val lat: Double = 0.0
)