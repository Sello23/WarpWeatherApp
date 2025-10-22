package com.warp.warpweatherapp.core.di.module

import com.warp.warpweatherapp.data.api.WeatherAPI
import com.warp.warpweatherapp.data.util.Constants
import com.warp.warpweatherapp.data.util.serialization.json
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOpenWeatherAPI(): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            ).build()
            .create(WeatherAPI::class.java)
    }

}