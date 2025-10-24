package com.warp.warpweatherapp

import com.warp.warpweatherapp.data.api.WeatherAPI

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class WarpWeatherInstrumentedTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var weatherAPI: WeatherAPI

    @Inject
    lateinit var mockWebServer: MockWebServer

    private val responseBody = """
        {
            "city": {
                "id": 993800,
                "coord": {"lat": -26.2041, "lon": 28.0473},
                "country": "ZA",
                "name": "Johannesburg",
                "population": 2026469,
                "timezone": 7200
            },
            "cod": "200",
            "message": 0.0036,
            "cnt": 7,
            "list": [
                {
                    "dt": 1721642400,
                    "temp": {
                        "day": 20.2,
                        "min": 10.1,
                        "max": 25.0,
                        "night": 15.3,
                        "eve": 22.1,
                        "morn": 11.2
                    },
                    "feels_like": {
                        "day": 19.5,
                        "night": 14.8,
                        "eve": 21.3,
                        "morn": 10.7
                    },
                    "pressure": 1015,
                    "humidity": 56,
                    "speed": 3.4,
                    "deg": 200,
                    "clouds": 10,
                    "pop": 0.1,
                    "weather": [
                        {
                            "id": 800,
                            "main": "Clear",
                            "description": "sky is clear",
                            "icon": "01d"
                        }
                    ]
                }
            ]
        }
    """.trimIndent()

    @Before
    fun setup() {
        hiltRule.inject()
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseBody)
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun should_get_weather_forecast_using_city_name() = runBlocking {
        val result = weatherAPI.getWeatherUpdate("Johannesburg")

        assertNotNull(result)
        assertEquals("Johannesburg", result.city.name)
        assertEquals(20.2, result.list.firstOrNull()?.temp?.day)
        assertEquals("ZA", result.city.country)
    }

    @Test
    fun should_get_weather_forecast_using_coordinates() = runBlocking {
        val result = weatherAPI.getWeatherUpdate(
            latitude = -26.2041,
            longitude = 28.0473
        )

        assertNotNull(result)
        assertEquals("Johannesburg", result.city.name)
        assertEquals(20.2, result.list.firstOrNull()?.temp?.day)
    }
}