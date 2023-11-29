package com.example.weatherassist.components

//import statements
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.weatherassist.screens.WeatherScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    modifier: Modifier = Modifier,
    title: String = "Rewari",
    isMainScreen: Boolean = true,
    navController: NavController,
    onSearchClicked: (() -> Unit)? = null
) {
    // State to track the expanded state of the dropdown menu
    val expanded = remember {
        mutableStateOf(false)
    }

    // Customized top app bar with center-aligned title and various actions
    CenterAlignedTopAppBar(
        title = {
            Text(title)
        },
        actions = {
            // Actions for the app bar, including search and options menu
            if (isMainScreen) {
                // Search Icon
                IconButton(onClick = onSearchClicked!!) {
                    Icon(imageVector = Icons.Default.Search, "Search Icon")
                }

                // More Options Dropdown Menu
                IconButton(onClick = { expanded.value = !expanded.value }) {
                    Icon(imageVector = Icons.Default.MoreVert, "Options")
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        // Dropdown menu items for  About and Settings
                        DropdownMenuItem(
                            text = { Text("About") },
                            onClick = {
                                navController.navigate(WeatherScreens.AboutScreen.name)
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Info, contentDescription = "About")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = {
                                navController.navigate(WeatherScreens.SettingScreen.name)
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Settings, contentDescription = "Settings")
                            }
                        )
                    }
                }
            }
        },
        // Navigation Icon
        modifier = modifier,
        navigationIcon = {

            val context = LocalContext.current
            if (!isMainScreen) {
                // If not on the main screen, show the back arrow icon
                IconButton(
                    onClick = {
                        navController.navigateUp()
                    }
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, "Back Arrow Icon")
                }
            }

        }
    )
}
