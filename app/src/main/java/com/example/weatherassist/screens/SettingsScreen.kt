package com.example.weatherassist.screens

// Import necessary dependencies
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weatherassist.components.CustomDialog
import com.example.weatherassist.components.WeatherAppBar
import com.example.weatherassist.viewModel.PreferencesViewModel
import kotlinx.coroutines.launch

// Composable function for the SettingsScreen, responsible for displaying user preferences.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    preferencesViewModel: PreferencesViewModel
) {
    // State to manage the bottom sheet for selecting the unit system.
    val sheetState = rememberModalBottomSheetState()

    // Collecting default city and unit from PreferencesViewModel
    val defaultCity = preferencesViewModel.defaultCity.collectAsState().value
    val defaultUnit = preferencesViewModel.defaultUnit.collectAsState().value

    // State to control the visibility of the custom dialog for setting the default city.
    val showDialog = remember {
        mutableStateOf(false)
    }

    // Scaffold composable providing the app structure with a top app bar.
    Scaffold(
        topBar = {
            WeatherAppBar(navController = navController, isMainScreen = false, title = "Settings")
        }
    ) {
        // State to manage the visibility of the unit system bottom sheet.
        val openUnitSheet = remember {
            mutableStateOf(false)
        }

        // Display the custom dialog for setting the default city when showDialog is true.
        if (showDialog.value) {
            CustomDialog(setShowDialog = { showDialog.value = it }) { city ->
                preferencesViewModel.saveDefaultCity(city)
                showDialog.value = false
            }
        }

        // Main column for displaying various user preferences.
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(10.dp)
        ) {
            // Row for the dynamic theme toggle switch.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Displaying the title and the switch for the dynamic theme.
                val switchState = preferencesViewModel.isDefaultThemeDynamic.collectAsState()
                var switch = switchState.value
                Text("Dynamic Theme", style = MaterialTheme.typography.titleMedium)
                Switch(
                    checked = switch,
                    onCheckedChange = {
                        switch = it
                        preferencesViewModel.saveDynamicTheme(switch)
                    },
                    modifier = Modifier.padding(end = 10.dp)
                )
            }

            // Row for selecting the unit system with a clickable area.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clickable {
                        openUnitSheet.value = true
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Displaying the title, default unit, and arrow icon for the unit system.
                Text("Unit System", style = MaterialTheme.typography.titleMedium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = defaultUnit, modifier = Modifier.alpha(0.8f))
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 4.dp, end = 5.dp)
                    )
                }
            }

            // Row for setting the default city with a clickable area.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clickable {
                        showDialog.value = true
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Displaying the title, default city, and arrow icon for setting the default city.
                Text("Default City", style = MaterialTheme.typography.titleMedium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = defaultCity, modifier = Modifier.alpha(0.8f))
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 4.dp, end = 5.dp)
                    )
                }
            }
        }

        // Displaying the unit system bottom sheet when openUnitSheet is true.
        if (openUnitSheet.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    openUnitSheet.value = false
                },
                sheetState = sheetState,
            ) {
                // Content of the unit system bottom sheet.
                UnitSheetContent(preferencesViewModel, openUnitSheet, sheetState)
            }
        }
    }
}

// Composable function for the content of the unit system bottom sheet.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitSheetContent(
    preferencesViewModel: PreferencesViewModel,
    openBottomSheet: MutableState<Boolean>,
    sheetState: SheetState
) {
    // CoroutineScope for launching asynchronous tasks.
    val scope = rememberCoroutineScope()

    // Column for displaying buttons to select the unit system.
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.height(10.dp))

        // Button for selecting Imperial unit system.
        Button(
            onClick = {
                preferencesViewModel.saveDefaultUnit("Imperial")
                scope.launch {
                    sheetState.hide()
                    openBottomSheet.value = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Imperial", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Button for selecting Metric unit system.
        Button(
            onClick = {
                preferencesViewModel.saveDefaultUnit("Metric")
                scope.launch {
                    sheetState.hide()
                    openBottomSheet.value = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Metric", fontSize = 20.sp)
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
}

