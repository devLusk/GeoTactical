package com.github.devlusk.geotactical.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.devlusk.geotactical.data.model.LocationData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.util.Locale

class LocationUtils(val context: Context) {

    private val fusedLocationCliente: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var locationCallback: LocationCallback? = null

    // Get location once
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(onLocationReceived: (LocationData) -> Unit) {
        fusedLocationCliente.lastLocation.addOnSuccessListener { location ->
            location?.let {
                val locationData = LocationData(
                    latitude = it.latitude,
                    longitude = it.longitude
                )
                onLocationReceived(locationData)
            }
        }
    }

    // Get location continuously
    @SuppressLint("MissingPermission")
    fun startLocationUpdates(
        intervalMs: Long = 1000,
        onLocationUpdate: (LocationData) -> Unit
    ) {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)

                result.lastLocation?.let {
                    val locationData = LocationData(
                        latitude = it.latitude,
                        longitude = it.longitude
                    )
                    onLocationUpdate(locationData)
                }
            }
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            intervalMs
        ).build()

        locationCallback?.let {
            fusedLocationCliente.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    fun stopLocationUpdates() {
        locationCallback?.let {
            fusedLocationCliente.removeLocationUpdates(it)
        }
    }

    fun reverseGeocodeLocation(locationData: LocationData): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(
            locationData.latitude,
            locationData.longitude,
            1
        )
        return addresses?.firstOrNull()?.getAddressLine(0) ?: "Address not found"
    }

    fun getNeighborhood(locationData: LocationData): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(
            locationData.latitude,
            locationData.longitude,
            1
        )

        return addresses?.firstOrNull()?.let { address ->
            address.subLocality ?: address.locality
        } ?: "Unknown"
    }

    fun hasLocationPermission(): Boolean {
        return (
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                ) || (
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                )
    }
}