package com.warp.warpweatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.warp.warpweatherapp.core.LocationManager
import com.warp.warpweatherapp.data.model.LocationPermissionState
import com.warp.warpweatherapp.data.util.NetworkMonitor
import com.warp.warpweatherapp.ui.WarpWeatherApp
import com.warp.warpweatherapp.ui.components.WarpWeatherAlertDialog
import com.warp.warpweatherapp.ui.rememberWarpWeatherAppState
import com.warp.warpweatherapp.ui.theme.WarpWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var locationManager: LocationManager
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.setLocationPermissionGranted(areLocationPermissionsAlreadyGranted())

        setContent {
            WarpWeatherAppTheme {
                val permissionGranted by viewModel.locationPermissionGranted.collectAsStateWithLifecycle()

                val locationPermissions = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

                var locationPermissionsGranted by remember {
                    mutableStateOf(areLocationPermissionsAlreadyGranted())
                }

                var shouldShowPermissionRationale by remember {
                    mutableStateOf(
                        shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
                    )
                }

                var currentPermissionsStatus by remember {
                    mutableStateOf(
                        decideCurrentPermissionStatus(
                            locationPermissionsGranted,
                            shouldShowPermissionRationale
                        )
                    )
                }

                val locationPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { permissions ->
                        locationPermissionsGranted = permissions.values.reduce { acc, granted ->
                            acc && granted
                        }

                        if (!locationPermissionsGranted) {
                            shouldShowPermissionRationale =
                                shouldShowRequestPermissionRationale(
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                        }

                        currentPermissionsStatus = decideCurrentPermissionStatus(
                            locationPermissionsGranted,
                            shouldShowPermissionRationale
                        )

                        viewModel.setLocationPermissionGranted(
                            currentPermissionsStatus == LocationPermissionState.Granted.name
                        )
                    }
                )

                val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

                DisposableEffect(
                    key1 = lifecycleOwner,
                    effect = {
                        val observer = LifecycleEventObserver { _, event ->
                            if (event == Lifecycle.Event.ON_START &&
                                !locationPermissionsGranted &&
                                !shouldShowPermissionRationale
                            ) {
                                locationPermissionLauncher.launch(locationPermissions)
                            }

                            if (event == Lifecycle.Event.ON_RESUME) {
                                val granted = areLocationPermissionsAlreadyGranted()
                                viewModel.setLocationPermissionGranted(granted)
                            }
                        }
                        lifecycleOwner.lifecycle.addObserver(observer)
                        onDispose {
                            lifecycleOwner.lifecycle.removeObserver(observer)
                        }
                    },
                )

                val scope = rememberCoroutineScope()

                Scaffold { innerPadding ->

                    if (shouldShowPermissionRationale) {
                        AlertDialog(
                            title = { Text("Permissions Required") },
                            text = { Text("You need to accept permissions to use this app.") },
                            onDismissRequest = {},
                            confirmButton = {
                                Button(
                                    onClick = {
                                        scope.launch {
                                            shouldShowPermissionRationale = false
                                            locationPermissionLauncher.launch(locationPermissions)
                                        }
                                    },
                                ) {
                                    Text(text = "Approve")
                                }
                            },
                            dismissButton = {
                                Button(
                                    onClick = {
                                        shouldShowPermissionRationale = false
                                        this.finish()
                                    }
                                ) {
                                    Text(text = "Close App")
                                }
                            }
                        )

                        WarpWeatherAlertDialog(
                            onDismissRequest = {},
                            title = { Text("Permissions Required") },
                            text = { Text("You need to accept permissions to use this app.") },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        scope.launch {
                                            shouldShowPermissionRationale = false
                                            locationPermissionLauncher.launch(locationPermissions)
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary.copy(
                                            alpha = 0.9f
                                        ),
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                ) {
                                    Text("Approve")
                                }
                            },
                            dismissButton = {
                                OutlinedButton(
                                    onClick = {
                                        shouldShowPermissionRationale = false
                                        this.finish()
                                    },
                                    border = BorderStroke(
                                        1.dp,
                                        MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                                    )
                                ) {
                                    Text("Close App")
                                }
                            }
                        )
                    }

                    if (permissionGranted) {
                        val appState = rememberWarpWeatherAppState(
                            networkMonitor = networkMonitor,
                            locationManager = locationManager
                        )
                        WarpWeatherApp(appState, innerPadding)
                    }
                }
            }
        }
    }

    private fun areLocationPermissionsAlreadyGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun decideCurrentPermissionStatus(
        locationPermissionsGranted: Boolean,
        shouldShowPermissionRationale: Boolean
    ): String {
        return when {
            locationPermissionsGranted -> LocationPermissionState.Granted.name
            shouldShowPermissionRationale -> LocationPermissionState.Rejected.name
            else -> LocationPermissionState.Denied.name
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WarpWeatherApp(
    appState: com.warp.warpweatherapp.ui.WarpWeatherAppState,
    innerPadding: PaddingValues
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WarpWeatherApp(appState)
        }
    }
}