package com.example.weatherassist.model

// Define a data class named "WeatherDetails" representing detailed weather information
data class WeatherDetails(
    // Cloud information
    val clouds: Clouds,

    // Time of data forecasted in Unix timestamp
    val dt: Int,

    // Date and time in text format
    val dt_txt: String,

    // Main weather-related information
    val main: Main,

    // Probability of precipitation as a percentage
    val pop: Double,

    // Rain information
    val rain: Rain,

    // System-related information
    val sys: Sys,

    // Visibility in meters
    val visibility: Int,

    // List of weather conditions
    val weather: List<WeatherX>,

    // Wind information
    val wind: Wind
)
