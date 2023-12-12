// Package declaration for the data-related components of the WeatherAssist app.
package com.example.weatherassist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.weatherassist.model.Favorite
import kotlinx.coroutines.flow.Flow

// Database access object interface for interacting with the local database.
@Dao
interface DatabaseDao {

    // Query to retrieve all favorite items from the favorite_table
    @Query("Select * from favorite_table")
    fun getAllFavorites(): Flow<List<Favorite>>

    // Query to retrieve a favorite item by its city from the favorite_table
    @Query("Select * from favorite_table where city = :city")
    suspend fun getFavoriteById(city: String): Favorite

    // Upsert (insert or update) operation for a favorite item in the favorite_table
    @Upsert
    suspend fun upsertFavorite(favoriteItem: Favorite)

    // Delete operation for a specific favorite item from the favorite_table
    @Delete
    suspend fun deleteFavorite(favoriteItem: Favorite)

    // Query to delete all favorite items from the favorite_table
    @Query("Delete from favorite_table")
    suspend fun deleteAllFavorites()
}
