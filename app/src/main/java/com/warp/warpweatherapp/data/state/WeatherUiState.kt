package com.warp.warpweatherapp.data.state

import com.warp.warpweatherapp.data.model.WeatherForecast

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Success(val data: WeatherForecast) : WeatherUiState
    data class Error(val message: String, val exception: Throwable? = null) : WeatherUiState
    data object Empty : WeatherUiState
}