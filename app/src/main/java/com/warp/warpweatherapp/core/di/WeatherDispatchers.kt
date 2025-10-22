package com.warp.warpweatherapp.core.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val weatherDispatchers: WeatherDispatchers)
enum class WeatherDispatchers {
    Default,
    IO,
    Main,
    MainImmediate,
}