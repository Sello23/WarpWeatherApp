package com.warp.warpweatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun WeatherGradientBackground(
    condition: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    val gradientColors = remember(condition, colorScheme) {
        when {
            "clear" in condition.lowercase() -> listOf(
                colorScheme.primary.copy(alpha = 0.15f),
                colorScheme.background
            )
            "cloud" in condition.lowercase() -> listOf(
                Color(0xFF546E7A).copy(alpha = 0.25f),
                colorScheme.background
            )
            "rain" in condition.lowercase() -> listOf(
                Color(0xFF1565C0).copy(alpha = 0.3f),
                colorScheme.background
            )
            "snow" in condition.lowercase() -> listOf(
                Color(0xFFB3E5FC).copy(alpha = 0.25f),
                colorScheme.background
            )
            "storm" in condition.lowercase() || "thunder" in condition.lowercase() -> listOf(
                Color(0xFF512DA8).copy(alpha = 0.25f),
                colorScheme.background
            )
            "mist" in condition.lowercase() || "fog" in condition.lowercase() -> listOf(
                Color(0xFF78909C).copy(alpha = 0.2f),
                colorScheme.background
            )
            else -> listOf(
                colorScheme.primary.copy(alpha = 0.08f),
                colorScheme.background
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors = gradientColors)
            )
    ) {
        content()
    }
}