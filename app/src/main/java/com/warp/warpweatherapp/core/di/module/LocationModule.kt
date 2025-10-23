package com.warp.warpweatherapp.core.di.module

import android.content.Context
import com.warp.warpweatherapp.core.FusedLocationManager
import com.warp.warpweatherapp.core.LocationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideLocationManager(
        @ApplicationContext context: Context
    ): LocationManager = FusedLocationManager(context)
}