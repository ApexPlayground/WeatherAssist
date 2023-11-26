package com.example.weatherassist.model

// Declare a data class representing detailed weather information for a specific time interval
data class WeatherDetails(
    // Cloud information
    val clouds: Clouds,
    // Time of the forecasted data, in seconds since 1970
    val dt: Int,
    // Date and time in text format
    val dt_txt: String,
    // Main weather information
    val main: Main,
    // Probability of precipitation
    val pop: Double,
    // Rain information
    val rain: Rain,
    // System information
    val sys: Sys,
    // Visibility in meters
    val visibility: Int,
    // List of weather conditions
    val weather: List<WeatherX>,
    // Wind information
    val wind: Wind
)