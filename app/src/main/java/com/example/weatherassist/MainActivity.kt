package com.example.weatherassist

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherassist.navigation.WeatherNavigation
import com.example.weatherassist.pref.UserPref
import com.example.weatherassist.pref.UserPrefImpl
import com.example.weatherassist.ui.theme.WeatherAssistTheme
import com.example.weatherassist.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



//        val permissionsToRequest = arrayOf(
//
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//
//        )



        setContent {
//            WeatherAssistTheme{
//                val viewModel = viewModel<MainViewModel>()
//                val dialogQueue = viewModel.visiblePermissionDialogQueue
//
//                val locationPermissionResultLauncher = rememberLauncherForActivityResult(
//                    contract = ActivityResultContracts.RequestMultiplePermissions(),
//                    onResult = { perms ->
//                        permissionsToRequest.forEach { permission ->
//                            viewModel.onPermissionResult(
//                                permission = permission,
//                                isGranted = perms[permission] == true
//                            )
//                        }
//                    }
//                )
//
//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Button(onClick = {
//                        locationPermissionResultLauncher.launch(permissionsToRequest)
//                    }) {
//                        Text(text = "Request multiple permission")
//                    }
//                }
//
//                dialogQueue
//                    .reversed()
//                    .forEach { permission ->
//                        PermissionDialog(
//                            permissionTextProvider = when (permission) {
//
//                                Manifest.permission.ACCESS_FINE_LOCATION -> {
//                                    userLocationPermission()
//                                }
//                                Manifest.permission.ACCESS_COARSE_LOCATION -> {
//                                    userLocationPermission()
//                                }
//                                else -> return@forEach
//                            },
//                            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
//                                permission
//                            ),
//                            onDismiss = viewModel::dismissDialog,
//                            onOkClick = {
//                                viewModel.dismissDialog()
//                                locationPermissionResultLauncher.launch(
//                                    arrayOf(permission)
//                                )
//                            },
//                            onGoToAppSettingsClick = ::openAppSettings
//                        )
//                    }
//            }






            WeatherAssistTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherNavigation()
                }
            }
        }
    }



}

//fun Activity.openAppSettings() {
//    Intent(
//        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//        Uri.fromParts("package", packageName, null)
//    ).also(::startActivity)
//}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAssistTheme {
        Greeting("Android")
    }
}