package com.example.weatherassist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Define the Favorite data class
@Entity(tableName = "favorite_table")
data class Favorite(
    // Primary key for the database table
    @PrimaryKey
    val city: String
)
