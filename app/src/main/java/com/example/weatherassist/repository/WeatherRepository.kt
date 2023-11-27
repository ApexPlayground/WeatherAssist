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
        return try {
            val response = api.getWeather(query)
            if (response.list.isNotEmpty()) {
                // Return weather data when it's not empty
                DataOrException(data = response, isLoading = false)
            } else {
                // Handle the case where the response is empty (invalid city)
                DataOrException(data = null, exception = Exception("Invalid city"), isLoading = false)
            }
        } catch (e: Exception) {
            // Handle exceptions during network request
            DataOrException(exception = e)
        }
    }
}
