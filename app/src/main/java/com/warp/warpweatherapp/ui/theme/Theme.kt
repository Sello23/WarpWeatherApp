package com.warp.warpweatherapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val BrandFrostBlack = Color(0xFF0E0E0E)
val BrandFrostGrey = Color(0xFF1E1E1E)
val BrandFrostWhite = Color(0xFFEAEAEA)
val BrandFrostBlue = Color(0xFFB7C7D8)
val BrandAccentYellow = Color(0xFFF9D65C)

val LightColors = lightColorScheme(
    primary = BrandFrostBlack,
    onPrimary = BrandFrostWhite,
    secondary = BrandFrostGrey,
    onSecondary = BrandFrostWhite,
    tertiary = BrandFrostBlue,
    onTertiary = BrandFrostBlack,
    background = Color(0xFFF5F5F5),
    onBackground = BrandFrostBlack,
    surface = Color(0xFFEDEDED),
    onSurface = BrandFrostBlack
)

val DarkColors = darkColorScheme(
    primary = BrandFrostWhite,
    onPrimary = BrandFrostBlack,
    secondary = BrandFrostGrey,
    onSecondary = BrandFrostWhite,
    tertiary = BrandAccentYellow,
    onTertiary = BrandFrostBlack,
    background = BrandFrostBlack,
    onBackground = BrandFrostWhite,
    surface = Color(0xFF1A1A1A),
    onSurface = BrandFrostWhite
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
    val colors = DarkColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}