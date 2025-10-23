package com.warp.warpweatherapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    background = md_theme_dark_background,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    outline = md_theme_dark_outline,
    error = md_theme_dark_error,
    tertiary = md_theme_dark_tertiary
)

object WarpWeatherAlertDialogDefaults {

    val shape
        @Composable get() = MaterialTheme.shapes.large

    val containerColor
        @Composable get() = MaterialTheme.colorScheme.surfaceContainerHigh

    val iconContentColor
        @Composable get() = MaterialTheme.colorScheme.primary

    val titleContentColor
        @Composable get() = MaterialTheme.colorScheme.onSurface

    val textContentColor
        @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

    val tonalElevation = 6.dp
}

object WarpWeatherTextFieldDefaults {

    @Stable
    @Composable
    fun colors() = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
        cursorColor = MaterialTheme.colorScheme.onSurface,
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
        focusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
        unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
    )
}

@Composable
fun WarpWeatherAppTheme(
    content: @Composable () -> Unit
) {
    val colors = DarkColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}