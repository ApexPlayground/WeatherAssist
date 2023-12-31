package com.example.weatherassist.screens
// Import necessary dependencies
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherassist.R
import com.example.weatherassist.components.WeatherAppBar

// Composable for the About screen, providing information about the developer.
@Composable
fun AboutScreen(navController: NavHostController) {

    // Scaffold with top bar and content
    Scaffold(topBar = {
        // WeatherAppBar is a custom component for the app bar
        WeatherAppBar(navController = navController, title = "About", isMainScreen = false)
    }) {

        // Column to arrange UI elements vertically
        Column(Modifier.padding(it)) {

            // Box for displaying an icon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f),
                contentAlignment = Alignment.Center
            ) {

                // Icon for the About screen
                Icon(
                    painter = painterResource(id = R.drawable.sun_cloud),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            // Get the URI handler from the local context
            val uriHandler = LocalUriHandler.current
            val context = LocalContext.current

            // Row for social media icons and links
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {

                // GitHub icon and link
                IconButton(onClick = {
                    uriHandler.openUri("https://github.com/ApexPlayground")
                }, modifier = Modifier.size(50.dp)) {
                    Icon(
                        painterResource(id = R.drawable.github),
                        "Github",
                        modifier = Modifier.size(30.dp)
                    )
                }

                // LinkedIn icon and link
                IconButton(onClick = {
                    uriHandler.openUri("https://www.linkedin.com/in/divine-eboigbe-a63483205/")
                }, modifier = Modifier.size(50.dp)) {
                    Icon(
                        painterResource(id = R.drawable.linkedin),
                        "LinkedIn",
                        modifier = Modifier.size(30.dp)
                    )
                }

                // Gmail icon and link
                IconButton(
                    onClick = {
                        // Create an intent to send an email
                        val intent = Intent()
                        intent.setAction(Intent.ACTION_SENDTO)
                        intent.data = Uri.parse("mailto:")
                        intent.putExtra(Intent.EXTRA_EMAIL, "ogbedivine7@gmail.com")
                        context.startActivity(intent)
                    }, modifier = Modifier
                        .size(50.dp)
                        .padding(4.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.gmail),
                        "Gmail",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            // Row for displaying the app description and version
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                // App Version
                Column (
                    modifier = Modifier
                        .padding(top = 25.dp)
                ) {
                    // Title
                    Text(
                        text = "About App",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ), textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    )
                    // App information
                    Text(
                        text = "Weather Assist is a modern Android application designed to provide users with accurate and detailed weather information.\nVersion: 1.0.1",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

// Preview for the About screen
@Preview
@Composable
fun AboutScreenPreview(navController: NavHostController = rememberNavController()) {
    AboutScreen(navController = navController)
}
