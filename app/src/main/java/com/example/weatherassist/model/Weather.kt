package com.example.weatherassist.model

// Declare a data class representing weather-related information
data class Weather(
    // Details about the city
    val city: City,
    // Count of data points returned
    val cnt: Int,
    // Cod is the HTTP response code
    val cod: String,
    // List of weather details for multiple time intervals
    val list: List<WeatherDetails>,
    // Message information
    val message: Int
)