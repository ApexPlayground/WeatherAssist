package com.example.weatherassist.components

//Import statements
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
    Box(
        modifier = modifier,

    ) {
        // Row to arrange child composable horizontally with space evenly distributed
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            // Obtain the image URL for weather icon
            val imageUrl = weatherData.data?.list?.getOrNull(0)?.weather?.getOrNull(0)?.icon
                ?.let { "https://openweathermap.org/img/wn/${it}@2x.png" }

            // First Column
            Column() {
                // Weather Image
                imageUrl?.let { WeatherImage(imageUrl = it, modifier = Modifier.size(95.dp)) }

                // Weather Description
                val description =
                    weatherData.data?.list?.getOrNull(0)?.weather?.getOrNull(0)?.description
                        ?.replaceFirstChar {
                            it.uppercase()
                        } ?: "N/A"
                Text(
                    text = description,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            // Second Column
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxHeight()
            ) {
                // Date Information
                Text("Today", style = MaterialTheme.typography.titleMedium)

                // Current Temperature
                val temperature = weatherData.data?.list?.getOrNull(0)?.main?.temp?.toInt()
                Text(
                    text = temperature?.let { "$it°" } ?: "N/A",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 45.sp
                )

                // Feels Like Temperature
                val feelsLike = weatherData.data?.list?.getOrNull(0)?.main?.feels_like?.toInt()
                Text(
                    text = feelsLike?.let { "Feels like $it°" } ?: "Not available",
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 16.sp
                )
            }
        }
    }
}
