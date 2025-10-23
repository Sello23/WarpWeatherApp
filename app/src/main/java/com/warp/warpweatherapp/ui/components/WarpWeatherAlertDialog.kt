package com.warp.warpweatherapp.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.warp.warpweatherapp.ui.theme.WarpWeatherAlertDialogDefaults

@Composable
fun WarpWeatherAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    properties: DialogProperties = DialogProperties()
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        modifier = modifier,
        dismissButton = dismissButton,
        icon = icon,
        title = title,
        text = text,
        shape = WarpWeatherAlertDialogDefaults.shape,
        containerColor = WarpWeatherAlertDialogDefaults.containerColor,
        iconContentColor = WarpWeatherAlertDialogDefaults.iconContentColor,
        titleContentColor = WarpWeatherAlertDialogDefaults.titleContentColor,
        textContentColor = WarpWeatherAlertDialogDefaults.textContentColor,
        tonalElevation = WarpWeatherAlertDialogDefaults.tonalElevation,
        properties = properties
    )
}