package com.example.weatherassist.components

//import statements
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomDialog(
    setShowDialog: (Boolean) -> Unit,
    buttonOnClick: (String) -> Unit
) {
    // Dialog composable for a custom dialog
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        // Column to arrange child composable vertically
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            // Apply styling to the column
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp)) // Rounded corners
                .background(MaterialTheme.colorScheme.surfaceVariant) // Background color
                .padding(5.dp) // Padding around the column
        ) {
            // State to hold the entered city value
            val cityState = remember {
                mutableStateOf("")
            }

            // Spacer for vertical spacing
            Spacer(modifier = Modifier.height(10.dp))

            // Text composable to display a label
            Text("Enter Home City")

            // Spacer for vertical spacing
            Spacer(modifier = Modifier.height(10.dp))

            // OutlinedTextField for user input with two-way data binding
            OutlinedTextField(
                value = cityState.value,
                onValueChange = {
                    cityState.value = it
                }
            )

            // Spacer for vertical spacing
            Spacer(modifier = Modifier.height(10.dp))

            // Button to trigger an action when clicked
            Button(
                onClick = {
                    // Invoke the provided buttonOnClick function with the entered city value
                    buttonOnClick(cityState.value)
                }
            ) {
                // Text inside the button
                Text("Save")
            }
        }
    }
}
