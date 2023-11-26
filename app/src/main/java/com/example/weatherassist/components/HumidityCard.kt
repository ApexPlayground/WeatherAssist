package com.example.weatherassist.components
// import statements
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.weatherassist.R
import com.example.weatherassist.util.convertDecimal

@Composable
fun HumidityCard(
    modifier: Modifier = Modifier,
    elevation: Dp,
    wind: String,
    humidity: String,
    pressure: String,
    visibility: String,
    sunrise: String,
    sunset: String
) {
    // Card composable for displaying humidity-related information
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(elevation)
    ) {
        // Row to arrange child composables horizontally with space around
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)
        ) {
            // First Column
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Wind Information
                Icon(
                    painter = painterResource(id = R.drawable.wind),
                    contentDescription = "Wind",
                    Modifier.size(30.dp)
                )
                Text(text = wind)
                Text(
                    text = "Wind",
                    modifier = Modifier.alpha(0.8f),
                    style = MaterialTheme.typography.labelSmall
                )

                // Spacer for vertical spacing
                Spacer(Modifier.height(5.dp))

                // Sunrise Information
                Icon(
                    painter = painterResource(id = R.drawable.sunrise),
                    contentDescription = "Sunrise",
                    Modifier.size(30.dp)
                )
                Text(text = sunrise)
                Text(
                    text = "Sunrise",
                    modifier = Modifier.alpha(0.8f),
                    style = MaterialTheme.typography.labelSmall
                )
            }

            // Second Column
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Humidity Information
                Icon(
                    painter = painterResource(id = R.drawable.humidity),
                    contentDescription = "Humidity",
                    Modifier.size(30.dp)
                )
                Text(text = humidity + "%")
                Text(
                    text = "Humidity",
                    modifier = Modifier.alpha(0.8f),
                    style = MaterialTheme.typography.labelSmall
                )

                // Spacer for vertical spacing
                Spacer(Modifier.height(5.dp))

                // Visibility Information
                Icon(
                    painter = painterResource(id = R.drawable.visibility),
                    contentDescription = "Visibility",
                    Modifier.size(30.dp)
                )
                Text(text = convertDecimal(visibility) + "KM")
                Text(
                    text = "Visibility",
                    modifier = Modifier.alpha(0.8f),
                    style = MaterialTheme.typography.labelSmall
                )
            }

            // Third Column
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Pressure Information
                Icon(
                    painter = painterResource(id = R.drawable.pressure),
                    contentDescription = "Pressure",
                    Modifier.size(30.dp)
                )
                Text(text = pressure + " hPa")
                Text(
                    text = "Pressure",
                    modifier = Modifier.alpha(0.8f),
                    style = MaterialTheme.typography.labelSmall
                )

                // Spacer for vertical spacing
                Spacer(Modifier.height(5.dp))

                // Sunset Information
                Icon(
                    painter = painterResource(id = R.drawable.sunset),
                    contentDescription = "Sunset",
                    Modifier.size(30.dp)
                )
                Text(text = sunset)
                Text(
                    text = "Sunset",
                    modifier = Modifier.alpha(0.8f),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
