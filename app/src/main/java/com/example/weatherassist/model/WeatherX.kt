package com.example.weatherassist.model

// Declare a data class representing weather conditions
data class WeatherX(
    // Description of the weather condition
    val description: String,
    // Icon representing the weather condition
    val icon: String,
    // ID of the weather condition
    val id: Int,
    // Main category of the weather condition
    val main: String
)