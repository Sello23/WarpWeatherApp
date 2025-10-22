package com.warp.warpweatherapp.data.api

import androidx.annotation.Keep
import com.warp.warpweatherapp.BuildConfig
import com.warp.warpweatherapp.data.dto.Weather
import com.warp.warpweatherapp.data.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Keep
@Singleton
interface WeatherAPI {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeatherUpdate(
        @Query(value = "q") location: String,
        @Query(value = "appid") apiId: String = BuildConfig.apiKeySafe,
        @Query(value = "units") units: String = Constants.METRIC,
    ): Weather

    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeatherUpdate(
        @Query(value = "lat") latitude: Double,
        @Query(value = "lon") longitude: Double,
        @Query(value = "appid") apiId: String = BuildConfig.apiKeySafe,
        @Query(value = "units") units: String = Constants.METRIC,
    ): Weather
}