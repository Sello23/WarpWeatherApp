package com.warp.warpweatherapp.ui

import androidx.compose.ui.test.junit4.createComposeRule
import com.warp.warpweatherapp.data.location.LocationData
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
@HiltAndroidTest
class WarpWeatherAppStateTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    val networkMonitor = TestNetworkMonitor()

    val locationManager = TestLocationManager()

    private lateinit var state: WarpWeatherAppState

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun warpWeatherAppStateTest_whenNetworkMonitorIsOffline_StateIsOffline() =
        runTest(UnconfinedTestDispatcher()) {

            composeTestRule.setContent {
                state = rememberWarpWeatherAppState(
                    networkMonitor = networkMonitor,
                    coroutineScope = backgroundScope,
                    locationManager = locationManager
                )
            }

            backgroundScope.launch { state.isOffline.collect() }
            networkMonitor.setConnected(false)
            assertEquals(
                true,
                state.isOffline.value,
            )

            backgroundScope.launch { state.location.collect() }

            // Simulate start and update
            locationManager.startLocationUpdates()
            locationManager.emitLocation(-26.2041, 28.0473) // Johannesburg

            assertEquals(
                LocationData(latitude = -26.2041, longitude = 28.0473),
                state.location.value
            )

            // Simulate clearing
            locationManager.clearLocation()
            assertEquals(null, state.location.value)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun warpWeatherAppStateTest_whenLocationHasStarted_locationIsUpdated() =
        runTest(UnconfinedTestDispatcher()) {

            composeTestRule.setContent {
                state = rememberWarpWeatherAppState(
                    networkMonitor = networkMonitor,
                    coroutineScope = backgroundScope,
                    locationManager = locationManager
                )
            }

            backgroundScope.launch { state.location.collect() }

            // Simulate start and update
            locationManager.startLocationUpdates()
            locationManager.emitLocation(-26.2041, 28.0473) // Johannesburg

            assertEquals(
                LocationData(latitude = -26.2041, longitude = 28.0473),
                state.location.value
            )

            // Simulate clearing
            locationManager.clearLocation()
            assertEquals(null, state.location.value)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun warpWeatherAppStateTest_whenNetworkToggles_multipleTimes_stateStaysConsistent() = runTest(UnconfinedTestDispatcher()) {
        composeTestRule.setContent {
            state = rememberWarpWeatherAppState(
                networkMonitor = networkMonitor,
                coroutineScope = backgroundScope,
                locationManager = locationManager
            )
        }

        backgroundScope.launch { state.isOffline.collect() }

        networkMonitor.setConnected(false)
        assertEquals(true, state.isOffline.value)

        networkMonitor.setConnected(true)
        assertEquals(false, state.isOffline.value)

        networkMonitor.setConnected(false)
        assertEquals(true, state.isOffline.value)

        networkMonitor.setConnected(true)
        assertEquals(false, state.isOffline.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun warpWeatherAppStateTest_whenNetworkOffline_locationIsCleared() = runTest(UnconfinedTestDispatcher()) {
        composeTestRule.setContent {
            state = rememberWarpWeatherAppState(
                networkMonitor = networkMonitor,
                coroutineScope = backgroundScope,
                locationManager = locationManager
            )
        }

        backgroundScope.launch { state.location.collect() }

        locationManager.startLocationUpdates()
        locationManager.emitLocation(-26.2041, 28.0473)
        assertEquals(LocationData(-26.2041, 28.0473), state.location.value)

        // Simulate offline mode — app should reset or ignore location
        networkMonitor.setConnected(false)
        locationManager.clearLocation()

        assertEquals(null, state.location.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun warpWeatherAppStateTest_whenReconnected_locationUpdatesResume() = runTest(UnconfinedTestDispatcher()) {
        composeTestRule.setContent {
            state = rememberWarpWeatherAppState(
                networkMonitor = networkMonitor,
                coroutineScope = backgroundScope,
                locationManager = locationManager
            )
        }

        backgroundScope.launch { state.location.collect() }

        // Go offline and clear location
        networkMonitor.setConnected(false)
        locationManager.clearLocation()
        assertEquals(null, state.location.value)

        // Back online — resume updates
        networkMonitor.setConnected(true)
        locationManager.startLocationUpdates()
        locationManager.emitLocation(-33.9249, 18.4241) // Cape Town

        assertEquals(
            LocationData(-33.9249, 18.4241),
            state.location.value,
            "Expected location to resume updates when network reconnected"
        )
    }

}