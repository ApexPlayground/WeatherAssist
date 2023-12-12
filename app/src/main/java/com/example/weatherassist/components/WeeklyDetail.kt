package com.example.weatherassist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherassist.data.DataOrException
import com.example.weatherassist.model.Weather
import com.example.weatherassist.util.dayFormat
import com.example.weatherassist.util.timeFormat

@Composable
fun WeeklyDetail(
    weatherData: DataOrException<Weather, Boolean, Exception>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        items(weatherData.data?.list.orEmpty()) { dailyWeather ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Safe call operator and nullish coalescing operator used to handle nulls
                val imageUrl = dailyWeather.weather?.getOrNull(0)?.icon
                    ?.let { "https://openweathermap.org/img/wn/${it}@2x.png" }

                // Displaying the day and time of the weather information
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                            append(dayFormat(dailyWeather.dt))
                        }
                        append(", " + timeFormat(dailyWeather.dt))
                    },
                    fontWeight = FontWeight.Light
                )

                // Column to display weather image and description
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Safe call operator used to handle null imageUrl
                    imageUrl?.let { WeatherImage(imageUrl = it, modifier = Modifier.size(75.dp)) }

                    // Safe call operator used to handle null weather description
                    Text(
                        dailyWeather.weather?.getOrNull(0)?.description?.replaceFirstChar {
                            it.uppercase()
                        } ?: "Description not available",
                        modifier = Modifier,
                        fontSize = 12.sp
                    )
                }

                // Displaying the maximum and minimum temperatures for the day
                Text(
                    buildAnnotatedString {
                        append(dailyWeather.main?.temp_max?.toInt()?.toString() ?: "N/A")
                        append("°/")
                        withStyle(
                            SpanStyle(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                fontWeight = FontWeight.Light,
                                fontSize = 16.sp
                            )
                        ) {
                            append(dailyWeather.main?.temp_min?.toInt()?.toString() ?: "N/A")
                            append("°")
                        }
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }


        }
    }
}