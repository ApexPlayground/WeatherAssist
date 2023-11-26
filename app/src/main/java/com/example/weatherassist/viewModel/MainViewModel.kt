package com.example.weatherassist.viewModel

// Import necessary dependencies
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherassist.data.DataOrException
import com.example.weatherassist.model.Weather
import com.example.weatherassist.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// HiltViewModel annotation for dependency injection with Hilt.
@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    // Suspended function to get weather data for a specific city.
    suspend fun getWeather(city: String): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(city)
    }


}