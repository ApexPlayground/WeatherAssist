package com.example.weatherassist.repository

// Import necessary dependencies
import com.example.weatherassist.data.DataOrException
import com.example.weatherassist.data.DatabaseDao
import com.example.weatherassist.model.Favorite
import com.example.weatherassist.model.Weather
import com.example.weatherassist.network.WeatherApi
import javax.inject.Inject


// WeatherRepository class handles data retrieval from the API and interacts with the local database.
class WeatherRepository @Inject constructor(private val api: WeatherApi, private val dao: DatabaseDao) {


    // Function to get weather data for a given query
    suspend fun getWeather(query: String): DataOrException<Weather, Boolean, Exception> {
        return try {
            val response = api.getWeather(query)
            if (response.list.isNotEmpty()) {
                // Return weather data when it's not empty
                DataOrException(data = response, isLoading = false)
            } else {
                // Handle the case where the response is empty (invalid city)
                DataOrException(
                    data = null,
                    exception = Exception("Invalid city"),
                    isLoading = false
                )
            }
        } catch (e: Exception) {
            // Handle exceptions during network request
            DataOrException(exception = e)
        }
    }

    // Function to retrieve all favorite items from the local database
    fun getAllFavorites() = dao.getAllFavorites()

    // Function to retrieve a favorite item by its city from the local database
    suspend fun getFavoriteById(city: String) = dao.getFavoriteById(city)

    // Function to insert or update a favorite item in the local database
    suspend fun upsertFavorite(favoriteItem: Favorite) = dao.upsertFavorite(favoriteItem)

    // Function to delete a specific favorite item from the local database
    suspend fun deleteFavorite(favoriteItem: Favorite) = dao.deleteFavorite(favoriteItem)

    // Function to delete all favorite items from the local database
    suspend fun deleteAllFavorites() = dao.deleteAllFavorites()
}