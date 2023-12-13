package com.example.weatherassist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherassist.data.DatabaseDao
import com.example.weatherassist.model.Favorite

// Room database class for weather assist data, including favorites.
@Database(entities = [Favorite::class], version = 4, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    // Abstract method to get the DAO for Favorite entities.
    abstract fun getFavoriteDao(): DatabaseDao
}
