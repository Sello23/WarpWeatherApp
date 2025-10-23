package com.warp.warpweatherapp.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class WeatherCondition(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("main")
    val main: String = "",

    @SerialName("description")
    val description: String = "",

    @SerialName("icon")
    val icon: String = ""
)