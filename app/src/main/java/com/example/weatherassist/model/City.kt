package com.example.weatherassist.model

// Declare a data class representing information about a city
data class City(
    // Coordinate information for the city
    val coord: Coord,
    // Country code of the city
    val country: String,
    // Unique identifier for the city
    val id: Int,
    // Name of the city
    val name: String,
    // Population of the city
    val population: Int,
    // Timestamp of sunrise in the city (Unix timestamp)
    val sunrise: Int,
    // Timestamp of sunset in the city (Unix timestamp)
    val sunset: Int,
    // Timezone offset from UTC in seconds
    val timezone: Int
)