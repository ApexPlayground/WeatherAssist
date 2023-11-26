package com.example.weatherassist.pref

// Import necessary dependencies
import android.location.Location
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

// Define keys for storing preferences
val CITY_KEY = stringPreferencesKey("city")
val THEME_KEY = booleanPreferencesKey("theme")
val UNIT_KEY = stringPreferencesKey("unit")

// Declare the UserPref interface to manage user preferences
interface UserPref {

    // Retrieve the default city preference as a Flow
    fun getDefaultCity(): Flow<String>

    // Retrieve the dynamic theme preference as a Flow
    fun getIsDynamicTheme(): Flow<Boolean>

    // Retrieve the default unit preference as a Flow
    fun getDefaultUnit(): Flow<String>

    // Save the default city preference
    suspend fun saveDefaultCity(city: String)

    // Save the dynamic theme preference
    suspend fun saveDynamicTheme(isDynamic: Boolean)

    // Save the default unit preference
    suspend fun saveDefaultUnit(unit: String)
}
