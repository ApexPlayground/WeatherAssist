
package com.example.weatherassist.model

// Data class representing a city
data class City(
    // Coordinates of the city
    val coord: Coord,

    // Country of the city
    val country: String,

    // Unique identifier for the city
    val id: Int,

    // Name of the city
    val name: String,

    // Population of the city
    val population: Int,

    // Time of sunrise in the city (Unix timestamp)
    val sunrise: Int,

    // Time of sunset in the city (Unix timestamp)
    val sunset: Int,

    // Timezone offset of the city in seconds
    val timezone: Int
)
