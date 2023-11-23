package com.example.weatherassist.model

// Import annotations for Room database
import androidx.room.Entity
import androidx.room.PrimaryKey

// Define a Room entity named "Favorite" representing a favorite city
@Entity(tableName = "favorite_table")
data class Favorite(

    @PrimaryKey
    // Property representing the favorite city
    val city: String
)
