package com.example.weatherassist.screens

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ShowLocationPermissionDeniedDialog(
    onDismiss: () -> Unit
) {
    // Get the current context
    val context = LocalContext.current

    // Display an AlertDialog with information about denied location permission
    AlertDialog(
        backgroundColor = MaterialTheme.colorScheme.inverseOnSurface,
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Location Permission Denied",
                style = androidx.compose.material.MaterialTheme.typography.h6.copy(
                    color =  MaterialTheme.colorScheme.onSurface
                )
            )
        },
        text = {
            Column {
                Text(
                    text = "To use this feature, you need to grant location permission.",
                    style = androidx.compose.material.MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Please go to the app settings and enable location.",
                    style = androidx.compose.material.MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        },
        confirmButton = {
            // Button to navigate to app settings
            Button(
                onClick = {
                    // Open app settings when the user clicks the "Go to Settings" button
                    (context as? Activity)?.openAppSettings()
                }
            ) {
                Text("Go to Settings",
                    style = androidx.compose.material.MaterialTheme.typography.button.copy(
                        color = MaterialTheme.colorScheme.inverseOnSurface
                    )
                )
            }
        },
    )
}