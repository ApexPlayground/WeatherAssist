package com.example.weatherassist.navigation

// Import necessary classes and components for navigation
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherassist.screens.AboutScreen
import com.example.weatherassist.screens.FavoriteScreen
import com.example.weatherassist.screens.MainScreen
import com.example.weatherassist.screens.SearchScreen
import com.example.weatherassist.screens.SettingsScreen
import com.example.weatherassist.screens.SplashScreen
import com.example.weatherassist.screens.WeatherScreens
import com.example.weatherassist.viewModel.MainViewModel
import com.example.weatherassist.viewModel.PreferencesViewModel

// Define the main composable function for weather navigation
@Composable
fun WeatherNavigation() {
    // Create a navigation controller
    val navController = rememberNavController()

    // Set up the navigation host with its start destination and modifier
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name,
        modifier = Modifier.fillMaxSize()
    ) {
        // Composable for SplashScreen
        composable(WeatherScreens.SplashScreen.name) {
            // Create an instance of PreferencesViewModel using Hilt
            val preferencesViewModel: PreferencesViewModel = hiltViewModel()
            // Call the SplashScreen composable with the navigation controller and PreferencesViewModel
            SplashScreen(navController, preferencesViewModel)
        }

        // Composable for MainScreen with a dynamic city argument
        composable(
            route = "${WeatherScreens.MainScreen.name}/{city}",
            arguments = listOf(
                navArgument("city") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            // Extract the city argument from the navigation entry
            navBackStackEntry.arguments?.getString("city").let { city ->
                // Log the city for debugging purposes
                Log.d("Haha", "WeatherNavigation: $city")
                // Create an instance of MainViewModel using Hilt
                val mainViewModel = hiltViewModel<MainViewModel>()
                // Call the MainScreen composable with the navigation controller, MainViewModel, and city argument
                MainScreen(navController, mainViewModel, city)
            }
        }

        // Composable for SearchScreen
        composable(WeatherScreens.SearchScreen.name) {
            // Call the SearchScreen composable with the navigation controller
            SearchScreen(navController = navController)
        }

        //Composable for FavouriteScreen
        composable(WeatherScreens.FavoriteScreen.name) {
            // Call the FavouriteScreen composable with the navigation controller
            FavoriteScreen(navController)
        }

        // Composable for AboutScreen
        composable(WeatherScreens.AboutScreen.name) {
            // Call the AboutScreen composable with the navigation controller
            AboutScreen(navController)
        }

        // Composable for SettingsScreen
        composable(WeatherScreens.SettingScreen.name) {
            // Create an instance of PreferencesViewModel using Hilt
            val preferencesViewModel = hiltViewModel<PreferencesViewModel>()
            // Call the SettingsScreen composable with the navigation controller and PreferencesViewModel
            SettingsScreen(navController, preferencesViewModel)
        }
    }
}
