// Define the package for the location-related domain logic
package com.example.weatherassist.domain.location

// Import necessary dependencies and classes
import android.location.Location

// Declare an interface for the LocationTracker
interface LocationTracker {

    // Declare a suspend function to get the current location, returns a Location object or null
    suspend fun getCurrentLocation(): Location?
}
