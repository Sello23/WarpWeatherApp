package com.warp.warpweatherapp.data.repository

import com.warp.warpweatherapp.data.state.WeatherUiState
import android.util.Log
import com.warp.warpweatherapp.data.api.WeatherAPI
import javax.inject.Inject

interface WeatherRepository {
    suspend fun getWeatherByCity(city: String): WeatherUiState
    suspend fun getWeatherByCoordinates(lat: Double, lon: Double): WeatherUiState
}

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherAPI
) : WeatherRepository {

    override suspend fun getWeatherByCity(city: String): WeatherUiState {
        return try {
            Log.d("WeatherRepository", "Fetching weather for city: $city")
            val response = api.getWeatherUpdate(location = city)
            if (response.list.isNotEmpty()) {
                WeatherUiState.Success(response)
            } else {
                WeatherUiState.Empty
            }
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error fetching city weather: ${e.message}", e)
            WeatherUiState.Error("Unable to fetch weather for $city", e)
        }
    }

    override suspend fun getWeatherByCoordinates(lat: Double, lon: Double): WeatherUiState {
        return try {
            Log.d("WeatherRepository", "Fetching weather for coordinates: $lat, $lon")
            val response = api.getWeatherUpdate(latitude = lat, longitude = lon)
            if (response.list.isNotEmpty()) {
                WeatherUiState.Success(response)
            } else {
                WeatherUiState.Empty
            }
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error fetching coordinate weather: ${e.message}", e)
            WeatherUiState.Error("Unable to fetch weather for coordinates", e)
        }
    }
}