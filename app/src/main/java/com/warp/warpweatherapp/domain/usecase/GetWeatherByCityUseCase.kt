package com.warp.warpweatherapp.domain.usecase

import com.warp.warpweatherapp.data.repository.WeatherRepository
import com.warp.warpweatherapp.data.state.WeatherUiState
import javax.inject.Inject

class GetWeatherByCityUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): WeatherUiState {
        return repository.getWeatherByCity(city)
    }
}