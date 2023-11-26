package com.example.weatherassist.screens

import android.Manifest
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
                Toast.makeText(context, "Permission already granted", Toast.LENGTH_SHORT).show()


            } else {
                // Permission is denied, handle it accordingly
                // You might want to show a message to the user or take appropriate action
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