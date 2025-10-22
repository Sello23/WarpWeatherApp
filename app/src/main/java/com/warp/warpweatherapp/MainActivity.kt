package com.warp.warpweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.warp.warpweatherapp.data.util.NetworkMonitor
import com.warp.warpweatherapp.ui.rememberWarpWeatherAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var networkMonitor: NetworkMonitor
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.darkTheme
//                    .collect { isDark ->
//                        darkTheme = isDark
//                        enableEdgeToEdge(
//                            statusBarStyle = SystemBarStyle.auto(
//                                lightScrim = android.graphics.Color.TRANSPARENT,
//                                darkScrim = android.graphics.Color.TRANSPARENT,
//                            ) { isDark },
//                            navigationBarStyle = SystemBarStyle.auto(
//                                lightScrim = lightScrim,
//                                darkScrim = darkScrim,
//                            ) { isDark },
//                        )
//                    }
//            }
//        }

        setContent {
            val appState = rememberWarpWeatherAppState(
                networkMonitor = networkMonitor,
            )
        }
    }
}