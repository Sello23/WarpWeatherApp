package com.warp.warpweatherapp.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class City(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("name")
    val name: String = "",

    @SerialName("coord")
    val coord: Coord = Coord(),

    @SerialName("country")
    val country: String = "",

    @SerialName("population")
    val population: Int = 0,

    @SerialName("timezone")
    val timezone: Int = 0
)