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
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherassist.MainActivity
import com.example.weatherassist.components.WeatherAppBar
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.Locale


// Function to request location updates
fun requestLocationUpdates(
    context: Context,
    onLocationResult: (Location) -> Unit,
    onPermissionDenied: () -> Unit
) {
    // Check if the location permission is granted
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val locationRequest = LocationRequest.create().apply {
            interval = 10000 // 10 seconds
            fastestInterval = 5000 // 5 seconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let {
                    // Invoke the callback with the user's current location
                    onLocationResult(it)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    } else {
        // If permission is not granted, request it
        onPermissionDenied()
    }
}

@Composable
fun SearchScreen(
    navController: NavController
) {
    val context = LocalContext.current
    // Create an instance of ActivityResultLauncher
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, request location updates
                requestLocationUpdates(
                    context = context,
                    onLocationResult = { location ->
                        // Handle the user's current location (in string format)
                        val latitude = location.latitude
                        val longitude = location.longitude

                        // Use reverse geocoding to get the city
                        val geocoder = Geocoder(context, Locale.getDefault())
                        try {
                            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                            if (addresses != null) {
                                if (addresses.isNotEmpty()) {
                                    val country = addresses[0].countryCode
                                    val city = addresses[0]?.locality
                                    val locationString = "City: $city,Country Code: $country, Lat: $latitude, Long: $longitude"
                                    Toast.makeText(context, locationString, Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Unable to get city information", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } catch (e: IOException) {
                            Toast.makeText(context, "Error while getting city information", Toast.LENGTH_SHORT).show()
                            e.printStackTrace()
                        }
                    },
                    onPermissionDenied = {
                        // Handle the case where the user denies location permission
                        Toast.makeText(
                           context,
                            "Location permission denied",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Open app settings

                    }
                )


            } else {
                // Handle the case where the user denies permission
                Toast.makeText(
                    context,
                    "Location permission denied",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    )
    Column {

        // WeatherAppBar is a custom component for the app bar
        WeatherAppBar(
            navController = navController,
            title = "Search",
            isMainScreen = false,
        )

        // Button to locate the user
        LocateMeButton(locationPermissionLauncher)

        // SearchField is a custom component for the search field
        SearchField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            onSearch = {
                // Navigate to the MainScreen with the search query as an argument
                navController.navigate(WeatherScreens.MainScreen.name + "/${it}") {
                    // Pop up to the MainScreen, including it in the back stack
                    popUpTo(WeatherScreens.SearchScreen.name) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchField(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Remember the current search state using rememberSaveable
    val searchState = rememberSaveable {
        mutableStateOf("")
    }

    // Determine if the input is valid (not empty)
    val isValid = remember(searchState.value) {
        searchState.value.trim().isNotEmpty()
    }

    // Get the current keyboard controller
    val keyboardController = LocalSoftwareKeyboardController.current

    // OutlinedTextField is a Material 3 component for text input with an outline
    OutlinedTextField(
        modifier = modifier,
        value = searchState.value,
        onValueChange = {
            searchState.value = it
        },
        label = {
            Text(text = "Enter a city")
        },
        // Trailing icon for the search functionality
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                // Make the icon clickable and trigger the search
                modifier = Modifier.clickable {
                    onSearch(searchState.value)
                }
            )
        },
        singleLine = true,
        // Set keyboard options for the search action
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        // Define keyboard actions to handle the search action
        keyboardActions = KeyboardActions {
            if (!isValid) {
                // If the input is not valid, do nothing
                return@KeyboardActions
            } else {
                // If the input is valid, trigger the search, clear the input, and hide the keyboard
                onSearch(searchState.value)
                searchState.value = ""
                keyboardController?.hide()
            }
        }
    )
}


@Composable
fun LocateMeButton(locationPermissionLauncher: ActivityResultLauncher<String>) {
    Button(
        onClick = {
            // Call the requestLocation function when the button is clicked
            locationPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        },
        modifier = Modifier.padding(16.dp)
    ) {
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

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}