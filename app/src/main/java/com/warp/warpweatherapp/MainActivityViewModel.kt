package com.warp.warpweatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState: MutableStateFlow<MainActivityUiState> =
        MutableStateFlow(MainActivityUiState.Loading)
    val uiState: StateFlow<MainActivityUiState> = _uiState
    val darkTheme: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            _uiState.value = MainActivityUiState.Ready
        }
    }
    fun prefersDarkTheme(): Boolean = darkTheme.value
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data object Ready : MainActivityUiState
}