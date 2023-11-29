package com.example.weatherassist.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

@Composable
// Composable function to display a weather image based on the provided URL
fun WeatherImage(imageUrl: String, modifier: Modifier = Modifier) {


    // Using the `Image` composable with the `rememberImagePainter` to load and display the image
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "Weather Image",
        modifier = modifier
    )
}
