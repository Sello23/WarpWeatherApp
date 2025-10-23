package com.warp.warpweatherapp.data.util.serialization

import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}