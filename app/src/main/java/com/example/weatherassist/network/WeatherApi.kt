package com.example.weatherassist.network

// Import necessary dependencies
import com.example.weatherassist.model.Weather
import com.example.weatherassist.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

// Declare the WeatherApi interface as a singleton
@Singleton
interface WeatherApi {

    // Define the GET request for fetching weather data
    @GET("data/2.5/forecast")
    suspend fun getWeather(
        // Specify query parameters for the request
        @Query("q") query: String,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = "metric"
    ): Weather
}
