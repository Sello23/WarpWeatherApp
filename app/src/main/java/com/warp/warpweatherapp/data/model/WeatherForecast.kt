package com.warp.warpweatherapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecast(
    @SerialName("city")
    val city: City = City(),

    @SerialName("cod")
    val cod: String = "",

    @SerialName("message")
    val message: Double = 0.0,

    @SerialName("cnt")
    val count: Int = 0,

    @SerialName("list")
    val list: List<WeatherDay> = emptyList()
)