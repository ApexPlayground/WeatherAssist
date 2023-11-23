package com.example.weatherassist.model

// Define a data class named "Wind" representing wind information
data class Wind(
    // Wind direction in degrees
    val deg: Int,

    // Wind gust speed in meters per second
    val gust: Double,

    // Wind speed in meters per second
    val speed: Double
)
