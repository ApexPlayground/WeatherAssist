package com.example.weatherassist.model

// Define a data class named "Weather" representing weather information
data class Weather(
    // Information about the city
    val city: City,

    // Number of forecasted time periods
    val cnt: Int,

    // Status code from the server response
    val cod: String,

    // List of weather details for different time periods
    val list: List<WeatherDetails>,

    // Message from the server response
    val message: Int
)
