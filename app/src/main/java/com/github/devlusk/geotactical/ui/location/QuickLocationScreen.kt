package com.github.devlusk.geotactical.ui.location

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.devlusk.geotactical.data.model.UserLocation
import com.github.devlusk.geotactical.screens.location.components.PermissionDeniedState
import com.github.devlusk.geotactical.ui.location.components.CurrentNeighborhood
import com.github.devlusk.geotactical.ui.location.components.LocationDetails
import com.github.devlusk.geotactical.ui.location.components.LocationHeader
import com.github.devlusk.geotactical.ui.location.components.MapPlaceholder
import com.github.devlusk.geotactical.ui.location.components.PositionOptions
import com.github.devlusk.geotactical.ui.permission.PermissionInfoDialog
import com.github.devlusk.geotactical.util.LocationManager

@Composable
fun LocationScreen(locationManager: LocationManager) {
    var currentLocation by remember { mutableStateOf<UserLocation?>(null) }
    var currentAddress by remember { mutableStateOf("-") }
    var currentNeighborhood by remember { mutableStateOf("") }
    var isTracking by remember { mutableStateOf(false) }
    var hasPermission by remember { mutableStateOf(locationManager.hasLocationPermission()) }

    // Permissions launcher
    val permissionRequestLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        val isCoarsePermissionGranted =
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        val isFinePermissionGranted =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true

        hasPermission = isCoarsePermissionGranted || isFinePermissionGranted

        if (!hasPermission) {
            Log.d("GeoTactical", "Debug: Location Permission Denied")
        }
    }

    // Dialogue control
    var showPermissionDialog by remember { mutableStateOf(!locationManager.hasLocationPermission()) }

    if (showPermissionDialog) {
        PermissionInfoDialog(
            onConfirm = {
                showPermissionDialog = false
                permissionRequestLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            },
            onCancel = {
                showPermissionDialog = false
                hasPermission = false
            }
        )
    }

    if (!hasPermission && !showPermissionDialog) {
        PermissionDeniedState(
            onAllowButton = {
                permissionRequestLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
        )
        return
    }

    // Position Functions
    fun getPosition() {
        locationManager.getCurrentLocation { location ->
            currentLocation = location
            currentAddress = locationManager.reverseGeocodeLocation(location)
            currentNeighborhood = locationManager.getNeighborhood(location)
        }
    }

    fun toggleTracking() {
        if (isTracking) {
            locationManager.stopLocationUpdates()
            isTracking = false
        } else {
            locationManager.startLocationUpdates { location ->
                currentLocation = location
                currentAddress = locationManager.reverseGeocodeLocation(location)
                currentNeighborhood = locationManager.getNeighborhood(location)
            }
            isTracking = true
        }
    }

    // Main UI
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            LocationHeader({}) // TODO: Add navigation to configurations

            Spacer(modifier = Modifier.height(16.dp))

            MapPlaceholder()

            Spacer(modifier = Modifier.height(16.dp))

            CurrentNeighborhood(currentNeighborhood)

            Spacer(modifier = Modifier.height(16.dp))

            PositionOptions(
                isTracking = isTracking,
                onTrackingChange = { toggleTracking() },
                onGetPositionClick = { getPosition() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LocationDetails(
                latitude = currentLocation?.latitude?.toString() ?: "-",
                longitude = currentLocation?.longitude?.toString() ?: "-",
                fullAddress = currentAddress,
                status = when {
                    isTracking -> "Tracking active"
                    currentLocation != null -> "Position obtained"
                    else -> "Click to get position"
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LocationScreenPreview() {
    LocationScreen(
        locationManager = LocationManager(LocalContext.current)
    )
}