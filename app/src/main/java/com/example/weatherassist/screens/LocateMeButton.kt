package com.example.weatherassist.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.Locale

@Composable
fun LocateMeButton(
    locationPermissionLauncher: ActivityResultLauncher<String>,
    onLocationReceived: (Location, String?) -> Unit
) {
    // Display a button to locate the user
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                // Call the requestLocation function when the button is clicked
                locationPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                )
            },
            modifier = Modifier.padding(16.dp),
        ) {
            // Icon and text for the Locate Me button
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp)
            )
            Text(text = "Locate Me")
        }
    }
}

// Function to request location updates
fun requestLocationUpdates(
    context: Context,
    onLocationResult: (Location, String) -> Unit,
    onPermissionDenied: () -> Unit
) {
    // Check if the location permission is granted
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        // Initialize the fused location client
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        // Create a location request
        val locationRequest = LocationRequest.create() .apply {

        }

        // Define a callback for location updates
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let {
                    // Use Geocoder to get location information based on latitude and longitude
                    val geocoder = Geocoder(context, Locale.getDefault())
                    try {
                        val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                        if (!addresses.isNullOrEmpty()) {
                            // Extract city and country code from the address
                            val city = addresses[0].locality
                            val code = addresses[0].countryCode
                            val locationString = "$city, $code"

                            // Invoke the callback with the user's current location and locationString
                            onLocationResult(it, locationString)
                        } else {
                            // Handle the case where address information is not available
                            onLocationResult(it, "Location information not available")
                        }
                    } catch (e: IOException) {
                        // Handle the case where an error occurs during geocoding
                        onLocationResult(it, "Error getting location information")
                        e.printStackTrace()
                    }
                }
            }
        }

        // Request location updates
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }
}

// Extension function to open the app settings
fun Activity.openAppSettings() {
    // Open the app settings page
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}