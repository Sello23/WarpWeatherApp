package com.warp.warpweatherapp.data.util.serialization

import kotlinx.serialization.json.Json

val json = Json {
    prettyPrint = false
    ignoreUnknownKeys = true
}