package com.warp.warpweatherapp.core.di.module

import com.warp.warpweatherapp.data.util.ConnectivityManagerNetworkMonitor
import com.warp.warpweatherapp.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkMonitorModule {

    @Binds
    @Singleton
    fun bindNetworkMonitor(
        impl: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor
}