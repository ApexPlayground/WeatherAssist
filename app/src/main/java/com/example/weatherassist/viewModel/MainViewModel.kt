package com.example.weatherassist.viewModel

// Import necessary dependencies
import androidx.lifecycle.ViewModel
import com.example.weatherassist.data.DataOrException
import com.example.weatherassist.model.Weather
import com.example.weatherassist.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// HiltViewModel annotation for dependency injection with Hilt.
@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    // Function to get weather information for a given city
    // Uses DataOrException wrapper to handle success, loading, and error states
    suspend fun getWeather(city: String): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(city)
    }

}
