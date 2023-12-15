package com.example.weatherassist.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.weatherassist.components.WeatherAppBar
import com.example.weatherassist.viewModel.FavoriteViewModel


@Composable
fun FavoriteScreen(
    navController: NavHostController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    // Scaffold layout
    Scaffold(topBar = {
        // Custom WeatherAppBar with navigation controls
        WeatherAppBar(
            navController = navController,
            title = "Favorite Cities",
            isMainScreen = false,
        )
    }) {
        // Collect the list of favorite cities from the view model
        val favList = favoriteViewModel.favoriteList.collectAsState().value

        // LazyColumn
        LazyColumn(
            Modifier.padding(it)
                .padding(horizontal = 10.dp)
        ) {
            // Iterate through the list of favorite items
            items(favList) { favoriteItem ->
                // Row composes its children in a horizontal line
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable {
                            // Navigate to the MainScreen with the selected city as a parameter
                            navController.navigate(WeatherScreens.MainScreen.name + "/${favoriteItem.city}")
                        }
                ) {
                    // Display the city name with a specific font size and padding
                    Text(
                        favoriteItem.city,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, end = 10.dp, start = 30.dp)
                    )
                }

                // Add spacing between rows
                Spacer(modifier = Modifier.height(13.dp))
            }
        }
    }
}
