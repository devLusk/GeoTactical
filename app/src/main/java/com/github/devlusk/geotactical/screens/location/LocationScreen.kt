package com.github.devlusk.geotactical.screens.location

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
import com.github.devlusk.geotactical.data.location.LocationUtils
import com.github.devlusk.geotactical.screens.location.components.CurrentNeighborhood
import com.github.devlusk.geotactical.screens.location.components.LocationDetails
import com.github.devlusk.geotactical.screens.location.components.LocationHeader
import com.github.devlusk.geotactical.screens.location.components.MapPlaceholder
import com.github.devlusk.geotactical.screens.location.components.PermissionDeniedState
import com.github.devlusk.geotactical.screens.location.components.PositionOptions
import com.github.devlusk.geotactical.screens.permission.PermissionInfoDialog

@Composable
fun LocationScreen(
    locationUtils: LocationUtils
) {
    var hasPermission by remember { mutableStateOf(locationUtils.hasLocationPermission()) }

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
    var showPermissionDialog by remember { mutableStateOf(!locationUtils.hasLocationPermission()) }

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

            CurrentNeighborhood("") // TODO: Pass current neighborhood

            Spacer(modifier = Modifier.height(16.dp))

            PositionOptions(false, {}, {}) // TODO: More shit to do

            Spacer(modifier = Modifier.height(16.dp))

            LocationDetails(
                latitude = "-22.7554",
                longitude = "-47.0094",
                fullAddress = "Main Avenue, Downtown, City XP",
                status = "Click \"GET CURRENT POSITION\" or enable tracking."
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LocationScreenPreview() {
    LocationScreen(
        locationUtils = LocationUtils(LocalContext.current)
    )
}