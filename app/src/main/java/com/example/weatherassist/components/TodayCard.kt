package com.example.weatherassist.components

//Import statements
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherassist.data.DataOrException
import com.example.weatherassist.model.Weather


@Composable
fun TodayCard(
    modifier: Modifier = Modifier,
    elevation: Dp,
    weatherData: DataOrException<Weather, Boolean, Exception>
) {
    // Card composable for displaying today's weather information
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(elevation)
    ) {
        // Row to arrange child composable horizontally with space evenly distributed
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            // Obtain the image URL for weather icon
            val imageUrl =
                "https://openweathermap.org/img/wn/${weatherData.data!!.list[0].weather[0].icon}@2x.png"

            // First Column
            Column() {
                // Weather Image
                WeatherImage(imageUrl = imageUrl, modifier = Modifier.size(95.dp))

                // Weather Description
                Text(
                    text = weatherData.data!!.list[0].weather[0].description.replaceFirstChar {
                        it.uppercase()
                    },
                    style = MaterialTheme.typography.titleLarge
                )
            }

            // Second Column
            Column(
                verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxHeight()
            ) {
                // Date Information
                Text("Today", style = MaterialTheme.typography.titleMedium)

                // Current Temperature
                Text(
                    weatherData.data!!.list[0].main.temp.toInt().toString() + "°",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 45.sp
                )

                // Feels Like Temperature
                Text(
                    text = "Feels like " + weatherData.data!!.list[0].main.feels_like.toInt() + "°",
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 16.sp
                )
            }
        }
    }
}
