//package com.warp.warpweatherapp.ui.components
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun WeatherCard(weather: Weather) {
//    Surface(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxWidth(),
//        shape = RoundedCornerShape(24.dp),
//        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
//        tonalElevation = 8.dp
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(weather.city.name, style = MaterialTheme.typography.titleLarge)
//            Text("${weather.main.temp}°C", style = MaterialTheme.typography.displayMedium)
//            Text(
//                weather.weather.firstOrNull()?.description?.replaceFirstChar { it.uppercase() }
//                    ?: "Clear",
//                style = MaterialTheme.typography.bodyLarge
//            )
//        }
//    }
//}