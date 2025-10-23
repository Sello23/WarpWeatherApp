package com.warp.warpweatherapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warp.warpweatherapp.data.state.WeatherUiState
import com.warp.warpweatherapp.domain.usecase.GetWeatherByCityUseCase
import com.warp.warpweatherapp.domain.usecase.GetWeatherByCoordinatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getWeatherByCityUseCase: GetWeatherByCityUseCase,
    private val getWeatherByCoordinatesUseCase: GetWeatherByCoordinatesUseCase
) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val weatherState: StateFlow<WeatherUiState> = _weatherState

    fun fetchWeatherByCity(cityName: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherUiState.Loading
            _weatherState.value = getWeatherByCityUseCase(cityName)
        }
    }

    fun fetchWeatherByCoordinates(lat: Double, lon: Double) {
        viewModelScope.launch {
            _weatherState.value = WeatherUiState.Loading
            _weatherState.value = getWeatherByCoordinatesUseCase(lat, lon)
        }
    }
}