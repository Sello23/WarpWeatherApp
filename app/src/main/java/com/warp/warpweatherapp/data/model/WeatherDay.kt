package com.warp.warpweatherapp.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class WeatherDay(
    @SerialName("dt")
    val timestamp: Long = 0L,

    @SerialName("sunrise")
    val sunrise: Long = 0L,

    @SerialName("sunset")
    val sunset: Long = 0L,

    @SerialName("temp")
    val temp: Temp = Temp(),

    @SerialName("feels_like")
    val feelsLike: FeelsLike = FeelsLike(),

    @SerialName("pressure")
    val pressure: Int = 0,

    @SerialName("humidity")
    val humidity: Int = 0,

    @SerialName("weather")
    val weather: List<WeatherCondition> = emptyList(),

    @SerialName("speed")
    val speed: Double = 0.0,

    @SerialName("deg")
    val deg: Int = 0,

    @SerialName("gust")
    val gust: Double? = null,

    @SerialName("clouds")
    val clouds: Int = 0,

    @SerialName("pop")
    val precipitationProbability: Double = 0.0,

    @SerialName("rain")
    val rain: Double? = null
)
