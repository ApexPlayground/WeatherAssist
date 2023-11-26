package com.example.weatherassist

// Import necessary dependencies
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom Application class for initializing Hilt for dependency injection.
 */
@HiltAndroidApp
class WeatherApplication : Application() {
}
