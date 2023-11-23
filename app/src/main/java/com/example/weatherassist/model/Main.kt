package com.example.weatherassist.model

// Define a data class named "Main" representing weather-related information
data class Main(
    // Apparent temperature in Kelvin
    val feels_like: Double,

    // Ground level atmospheric pressure in hPa (hectopascals)
    val grnd_level: Int,

    // Humidity percentage
    val humidity: Int,

    // Atmospheric pressure in hPa (hectopascals)
    val pressure: Int,

    // Sea level atmospheric pressure in hPa (hectopascals)
    val sea_level: Int,

    // Temperature in Kelvin
    val temp: Double,

    // Temperature correction due to cloudiness in Kelvin
    val temp_kf: Double,

    // Maximum temperature in Kelvin
    val temp_max: Double,

    // Minimum temperature in Kelvin
    val temp_min: Double
)
