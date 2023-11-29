package com.example.weatherassist.screens
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import com.example.weatherassist.components.WeatherAppBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment



@Composable
fun SearchScreen(
    navController: NavController
) {
    // Get the current context
    val context = LocalContext.current

    // Remember the current search state using rememberSaveable
    val searchState = rememberSaveable {
        mutableStateOf("")
    }

    // State variable to track whether to show the location permission denied dialog
    var showLocationPermissionDeniedDialog by remember { mutableStateOf(false) }

    // Create an instance of ActivityResultLauncher to request location permission
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
                    onPermissionDenied = {}
                )
            } else {
                // Handles the case where the user denies permission
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

    // Compose UI elements
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

        // Box for displaying the Locate Me button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            contentAlignment = Alignment.Center
        ) {
            // Button to locate the user
            LocateMeButton(locationPermissionLauncher) { location, locationString ->
                // Update the search state with the location string
                searchState.value = locationString ?: ""
            }
        }
    }
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
    val context = LocalContext.current

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
            Text(text = "Enter a valid city")
        },
        // Trailing icon for the search functionality
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                // Make the icon clickable and trigger the search
                modifier = Modifier.clickable {
                    if (isValid) {
                        // If the input is valid, trigger the search, clear the input, and hide the keyboard
                        onSearch(searchState.value)
                        searchState.value = ""
                        keyboardController?.hide()
                    } else {
                        // If the input is not valid, show a toast to enter city
                        Toast.makeText(context, "Please enter a city", Toast.LENGTH_LONG).show()
                    }
                }
            )
        },
        singleLine = true,
        // Set keyboard options for the search action
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        // Define keyboard actions to handle the search action
        keyboardActions = KeyboardActions {
            if (isValid) {
                // If the input is valid, trigger the search, clear the input, and hide the keyboard
                onSearch(searchState.value)
                searchState.value = ""
                keyboardController?.hide()
            } else {
                // If the input is not valid, show a toast to enter city
                Toast.makeText(context, "Please enter a city", Toast.LENGTH_LONG).show()
            }
        }
    )
}


