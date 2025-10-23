package com.warp.warpweatherapp.data.util

import android.content.Context
import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun currentFormattedDate(): String {
    val date = Date()
    val formatter = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
    return formatter.format(date)
}

fun formatDecimals(item: Double): String {
    return " %.0f".format(item)
}

@Composable
fun fetchResourceId(name: String?, defType: String): Int {

    val context: Context = LocalContext.current
    val resources: Resources = context.resources
    val resourcesId =
        resources.getIdentifier(
            name,
            defType, context.packageName
        )

    return resourcesId
}