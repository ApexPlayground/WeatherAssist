package com.example.weatherassist.model

// Define a data class named "WeatherX" representing weather conditions
data class WeatherX(
    // Description of the weather condition
    val description: String,

    // Icon representing the weather condition
    val icon: String,

    // Unique identifier for the weather condition
    val id: Int,

    // Main category of the weather condition
    val main: String
)
