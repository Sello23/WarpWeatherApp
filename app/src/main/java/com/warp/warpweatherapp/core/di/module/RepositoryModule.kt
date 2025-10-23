package com.warp.warpweatherapp.core.di.module

import com.warp.warpweatherapp.data.api.WeatherAPI
import com.warp.warpweatherapp.data.repository.WeatherRepository
import com.warp.warpweatherapp.data.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherAPI): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }
}