package com.example.weatherassist.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

// Implementation of the UserPref interface using DataStore for storing user preferences.
class UserPrefImpl(private val dataStore: DataStore<Preferences>) : UserPref {

    // Retrieves the default city preference as a Flow.
    override fun getDefaultCity(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[CITY_KEY] ?: "Dublin,IE"
        }
    }

    // Retrieves the dynamic theme preference as a Flow.
    override fun getIsDynamicTheme(): Flow<Boolean> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[THEME_KEY] ?: false
        }
    }

    // Retrieves the default unit preference as a Flow.
    override fun getDefaultUnit(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[UNIT_KEY] ?: "Metric"
        }
    }

    // Saves the provided city as the default city preference.
    override suspend fun saveDefaultCity(city: String) {
        dataStore.edit {
            it[CITY_KEY] = city
        }
    }

    // Saves the provided dynamic theme preference.
    override suspend fun saveDynamicTheme(isDynamic: Boolean) {
        dataStore.edit {
            it[THEME_KEY] = isDynamic
        }
    }

    // Saves the provided unit as the default unit preference.
    override suspend fun saveDefaultUnit(unit: String) {
        dataStore.edit {
            it[UNIT_KEY] = unit
        }
    }
}
