package com.example.weatherassist.repository

// Import necessary dependencies
import com.example.weatherassist.data.DataOrException
import com.example.weatherassist.model.Weather
import com.example.weatherassist.network.WeatherApi
import javax.inject.Inject

// Declare the WeatherRepository class, which handles data retrieval from the API
class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    // Function to get weather data for a given query
    suspend fun getWeather(query: String): DataOrException<Weather, Boolean, Exception> {
        // Try to make a network request to get weather data
        val response = try {
            api.getWeather(query)
        } catch (e: Exception) {
            // If an exception occurs during the network request, return an exception in the result
            return DataOrException(exception = e)
        }

        // Return the weather data in the result, indicating that it's not in a loading state
        return DataOrException(data = response, isLoading = false)
    }
}
