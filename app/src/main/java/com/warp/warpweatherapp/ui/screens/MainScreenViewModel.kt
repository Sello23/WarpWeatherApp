package com.warp.warpweatherapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warp.warpweatherapp.data.state.WeatherUiState
import com.warp.warpweatherapp.domain.usecase.GetWeatherByCityUseCase
import com.warp.warpweatherapp.domain.usecase.GetWeatherByCoordinatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getWeatherByCityUseCase: GetWeatherByCityUseCase,
    private val getWeatherByCoordinatesUseCase: GetWeatherByCoordinatesUseCase
) : ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val weatherState: StateFlow<WeatherUiState> = _weatherState.asStateFlow()
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()
    private val _location = MutableStateFlow<Pair<Double, Double>?>(null)

    init {
        viewModelScope.launch {
            combine(
                _query.debounce(700).distinctUntilChanged(),
                _location
            ) { query, location ->
                query to location
            }
                .distinctUntilChanged()
                .collectLatest { (query, location) ->
                    when {
                        query.isNotBlank() -> fetchWeatherByCity(query)
                        location != null -> fetchWeatherByCoordinates(
                            location.first,
                            location.second
                        )

                        else -> _weatherState.value = WeatherUiState.Empty
                    }
                }
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun updateLocation(lat: Double, lon: Double) {
        _location.value = lat to lon
    }

    suspend fun fetchWeatherByCity(cityName: String) {
        _weatherState.value = WeatherUiState.Loading
        _weatherState.value = getWeatherByCityUseCase(cityName)
    }

    private suspend fun fetchWeatherByCoordinates(lat: Double, lon: Double) {
        _weatherState.value = WeatherUiState.Loading
        _weatherState.value = getWeatherByCoordinatesUseCase(lat, lon)
    }
}