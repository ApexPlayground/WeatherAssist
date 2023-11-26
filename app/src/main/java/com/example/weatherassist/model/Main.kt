package com.example.weatherassist.model

// Declare a data class representing main weather information
data class Main(
    // "Feels Like" temperature in degrees Celsius
    val feels_like: Double,
    // Ground level atmospheric pressure in hPa
    val grnd_level: Int,
    // Humidity percentage
    val humidity: Int,
    // Atmospheric pressure in hPa
    val pressure: Int,
    // Sea level atmospheric pressure in hPa
    val sea_level: Int,
    // Temperature in degrees Celsius
    val temp: Double,
    // Temperature variation from the previous three hours in degrees Celsius
    val temp_kf: Double,
    // Maximum temperature in degrees Celsius
    val temp_max: Double,
    // Minimum temperature in degrees Celsius
    val temp_min: Double
)