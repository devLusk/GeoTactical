package com.github.devlusk.geotactical.screens.location

import android.Manifest
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
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
import androidx.core.app.ActivityCompat
import com.github.devlusk.geotactical.MainActivity
import com.github.devlusk.geotactical.data.location.LocationUtils
import com.github.devlusk.geotactical.screens.PermissionInfoDialog
import com.github.devlusk.geotactical.screens.location.components.CurrentNeighborhood
import com.github.devlusk.geotactical.screens.location.components.LocationDetails
import com.github.devlusk.geotactical.screens.location.components.LocationHeader
import com.github.devlusk.geotactical.screens.location.components.MapPlaceholder
import com.github.devlusk.geotactical.screens.location.components.PositionOptions

@Composable
fun LocationScreen(
    locationUtils: LocationUtils
) {
    val context = LocalContext.current

    val permissionRequestLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permission ->
        val isCoarsePermissionGranted =
            permission[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        val isFinePermissionGranted =
            permission[Manifest.permission.ACCESS_FINE_LOCATION] == true

        if (isCoarsePermissionGranted || isFinePermissionGranted) {
            Log.d("GeoTactical", "Debug: Location Permission Granted")
        } else {
            val shouldShowRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )

            if (shouldShowRationale) {
                Toast.makeText(
                    context,
                    "Location permission is required to use this app",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "You can enable location permission later in Settings",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

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
                Toast.makeText(
                    context,
                    "You can enable location permission later in Settings",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }

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