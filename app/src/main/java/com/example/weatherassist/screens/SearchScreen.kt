package com.example.weatherassist.screens
// Import necessary dependencies
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale

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
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val locationRequest = LocationRequest.create().apply {

        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    try {
                        val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                        if (!addresses.isNullOrEmpty()) {
                            // val country = addresses[0].countryName
                            val city = addresses[0].locality
                            // val area = addresses[0].subLocality
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
    // Remember the current search state using rememberSaveable
    val searchState = rememberSaveable {
        mutableStateOf("")
    }

    // State variable to track whether to show the location permission denied dialog
    var showLocationPermissionDeniedDialog by remember { mutableStateOf(false) }

    // Create an instance of ActivityResultLauncher
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, request location updates
                requestLocationUpdates(
                    context = context,
                    onLocationResult = { _, locationString ->
                        // Use a MutableState to update the Compose state
                        searchState.value = locationString ?: ""
                    },

                    onPermissionDenied = {
                        showLocationPermissionDeniedDialog = true
                    }
                )
            } else {
                // Handles the case where the user denies permission
//                Toast.makeText(
//                    context,
//                    "To use this feature, you need to grant location permission.",
//                    Toast.LENGTH_LONG
//                ).show()
                showLocationPermissionDeniedDialog = true
            }
        }
    )

    // Show the dialog if the state variable is true
    if (showLocationPermissionDeniedDialog) {
        ShowLocationPermissionDeniedDialog(
            onDismiss = {
                // Reset the state variable to prevent showing the dialog repeatedly
                showLocationPermissionDeniedDialog = false
            }
        )
    }

    Column {
        // WeatherAppBar is a custom component for the app bar
        WeatherAppBar(
            navController = navController,
            title = "Search",
            isMainScreen = false,
        )

        // SearchField is a custom component for the search field
        SearchField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            searchState = searchState,
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

        // Button to locate the user
        LocateMeButton(locationPermissionLauncher) { location, locationString ->
            // Update the search state with the location string
            searchState.value = locationString ?: ""
        }
    }
}

@Composable
fun ShowLocationPermissionDeniedDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Location Permission Denied",
                style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.primary)
            )
        },
        text = {
            Column {
                Text(
                    text = "To use this feature, you need to grant location permission.",
                    style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Please go to the app settings and enable location.",
                    style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Open app settings when the user clicks the "Go to Settings" button
                    (context as? Activity)?.openAppSettings()
                }
            ) {
                Text("Go to Settings", style = MaterialTheme.typography.button)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchField(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    searchState: MutableState<String>
) {
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
fun LocateMeButton(
    locationPermissionLauncher: ActivityResultLauncher<String>,
    onLocationReceived: (Location, String?) -> Unit
) {
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
//            // Customize the button colors
//            colors = ButtonDefaults.buttonColors(
//               // backgroundColor = Color.Black,
//               contentColor = Color.White,
//                containerColor = Color.Black
//            )
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
}

// Extension function to open the app settings
fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}
