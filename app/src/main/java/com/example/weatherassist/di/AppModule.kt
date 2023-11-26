package com.example.weatherassist.di

//import statements
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.weatherassist.network.WeatherApi
import com.example.weatherassist.pref.UserPref
import com.example.weatherassist.pref.UserPrefImpl
import com.example.weatherassist.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Dagger Hilt module for providing dependencies across the application.
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provides a singleton instance of the WeatherApi interface for making network requests.
    @Singleton
    @Provides
    fun providesWeatherApi(): WeatherApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)




    // Provides a singleton instance of the DataStore<Preferences> for storing key-value preferences.
    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler {
                emptyPreferences()
            }, produceFile = { context.preferencesDataStoreFile("user_data") }
        )
    }

    // Provides a singleton instance of the UserPref interface for managing user preferences.
    @Provides
    @Singleton
    fun provideUserPref(dataStore: DataStore<Preferences>): UserPref = UserPrefImpl(dataStore)
}