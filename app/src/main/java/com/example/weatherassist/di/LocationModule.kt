// Define the package for the DI (Dependency Injection) module
package com.example.weatherassist.di

// Import necessary dependencies and classes
import com.example.weatherassist.data.location.DefaultLocationTracker
import com.example.weatherassist.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Declare a Dagger Hilt module for handling location-related dependencies
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    // Use Dagger's @Binds annotation to bind the concrete implementation (DefaultLocationTracker)
    // to the LocationTracker interface. This is done for Dependency Injection.
    @Binds
    @Singleton
    abstract fun bindLocationTracker(defaultLocationTracker: DefaultLocationTracker): LocationTracker
}
