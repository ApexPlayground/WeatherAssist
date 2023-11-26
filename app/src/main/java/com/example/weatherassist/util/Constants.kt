package com.example.weatherassist.util

// Object containing constants used in the Weather Assist app, such as API key and base URL.
object Constants {
    val API_KEY = "528cc118be553e59be46f38f4acfebf0"  // OpenWeatherMap API key
    val BASE_URL = "https://api.openweathermap.org"  // Base URL for OpenWeatherMap API
}

// Object containing constant titles for specific exceptions or errors in the app.
object ExceptionTitles {
    const val GPS_DISABLED = "GPS Disabled"   // Title for GPS disabled exception
    const val NO_PERMISSION = "No Permission"  // Title for no permission exception
}
