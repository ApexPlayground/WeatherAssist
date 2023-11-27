package com.example.weatherassist.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherassist.components.HumidityCard
import com.example.weatherassist.components.TodayCard
import com.example.weatherassist.components.WeatherAppBar
import com.example.weatherassist.components.WeeklyDetail
import com.example.weatherassist.data.DataOrException
import com.example.weatherassist.model.Weather
import com.example.weatherassist.repository.WeatherRepository
import com.example.weatherassist.util.timeFormat
import com.example.weatherassist.viewModel.MainViewModel



@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel, city: String?) {

    // Fetch weather data using produceState
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(isLoading = true)
    ) {
        value = mainViewModel.getWeather(city!!)
    }.value

    // Show loading indicator if data is still loading
    if (weatherData.isLoading == true) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
        // Log information about loading state
        Log.d("Message", "loading: ${weatherData.data.toString()}")
    } else {
        // Log information when weather data is available
        Log.d("Message", "MainScreen: ${weatherData.data.toString()}")

        // Show the main screen content using Scaffold
        Scaffold(
            topBar = {
                // WeatherAppBar with title and search functionality
                WeatherAppBar(
                    title = weatherData.data?.let { "${it.city.name}, ${it.city.country}" } ?: "N/A",
                    isMainScreen = true,
                    navController = navController,
                    onSearchClicked = {
                        navController.navigate(WeatherScreens.SearchScreen.name)
                    }
                )
            }
        ) {
            // Display the main screen content using MainScreenContent composable
            MainScreenContent(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                weatherData = weatherData
            )
        }
    }
}
@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    weatherData: DataOrException<Weather, Boolean, Exception>
) {

    // Column to arrange UI elements vertically in the center
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Display today's weather card
        TodayCard(
            elevation = 4.dp,
            weatherData = weatherData,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 10.dp,
                    start = 10.dp,
                    end = 10.dp
                )
                .height(150.dp)
        )

        // Display humidity card with weather details
        HumidityCard(
            elevation = 4.dp,
            wind = weatherData.data?.list?.getOrNull(0)?.wind?.speed.toString() ?: "N/A",
            humidity = weatherData.data?.list?.getOrNull(0)?.main?.humidity.toString() ?: "N/A",
            pressure = weatherData.data?.list?.getOrNull(0)?.main?.pressure.toString() ?: "N/A",
            visibility = weatherData.data?.list?.getOrNull(0)?.visibility.toString() ?: "N/A",
            sunrise = weatherData.data?.city?.sunrise?.let { timeFormat(it) } ?: "N/A",
            sunset = weatherData.data?.city?.sunset?.let { timeFormat(it) } ?: "N/A",
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    start = 10.dp,
                    end = 10.dp
                )
        )

        // Display text for the upcoming week
        Text("This Week", modifier = Modifier.padding(top = 5.dp))

        // Display weekly weather details using WeeklyDetail composable
        WeeklyDetail(
            weatherData, modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 5.dp,
                    start = 10.dp,
                    end = 10.dp
                )
                .clip(RoundedCornerShape(16.dp))
        )
    }
}

