package com.example.weatherassist.screens

// Import necessary dependencies

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherassist.R
import com.example.weatherassist.viewModel.PreferencesViewModel
import kotlinx.coroutines.delay

// Composable function for the SplashScreen, displayed when the app is launched.
@Composable
fun SplashScreen(
    navController: NavController,
    preferencesViewModel: PreferencesViewModel
) {
    // Collecting the default city from PreferencesViewModel
    val defaultCity = preferencesViewModel.defaultCity.collectAsState()
    Log.d("Message", ": ${defaultCity.value}")

    // scaling animation
    val scale = remember {
        Animatable(initialValue = 0f)
    }

    // LaunchedEffect to trigger animations and navigate to the MainScreen after a delay
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            0.9f,
            animationSpec = tween(800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }
            )
        )
        delay(200L)
        Log.d("Message", "SplashScreen: ${defaultCity.value}")

        // Navigating to the MainScreen with the default city as an argument
        navController.navigate(WeatherScreens.MainScreen.name + "/${defaultCity.value}") {
            // Popping up to the MainScreen and removing the SplashScreen from the back stack
            popUpTo(WeatherScreens.SplashScreen.name) {
                inclusive = true
            }
        }
    }

    // Box composable containing the animated icon
    Box(
        modifier = Modifier
            .fillMaxSize()
            .scale(scale.value),
        contentAlignment = Alignment.Center
    ) {
        // Circular container with border and the app's icon
        Box(
            modifier = Modifier
                .size(330.dp)
                .clip(CircleShape)
                .border(
                    2.dp, MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            // App's icon within the circular container
            Icon(
                painter = painterResource(id = R.drawable.sun_cloud),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}