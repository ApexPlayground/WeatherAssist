package com.example.weatherassist.model

// Declare a data class representing wind information
data class Wind(
    // Wind direction in degrees
    val deg: Int,
    // Gust speed of the wind
    val gust: Double,
    // Actual speed of the wind
    val speed: Double
)