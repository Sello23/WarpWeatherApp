package com.warp.warpweatherapp.domain.usecase

import com.warp.warpweatherapp.data.repository.WeatherRepository
import com.warp.warpweatherapp.data.state.WeatherUiState
import javax.inject.Inject

class GetWeatherByCoordinatesUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): WeatherUiState {
        return repository.getWeatherByCoordinates(lat, lon)
    }
}