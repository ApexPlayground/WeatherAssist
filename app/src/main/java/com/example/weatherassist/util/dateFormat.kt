package com.example.weatherassist.util
// Import necessary dependencies
import java.text.SimpleDateFormat
import java.util.Date

// Utility functions for date and time formatting in the Weather Assist app.

// Formats a timestamp to a date string.
fun dateFormat(timeStamp: Int): String {
    val sdf = SimpleDateFormat("EEE, MMM d")
    val date = Date(timeStamp.toLong() * 1000) // Convert to milliseconds
    return sdf.format(date)
}

// Formats a timestamp to a time string.
fun timeFormat(timeStamp: Int): String {
    val sdf = SimpleDateFormat("hh:mm aa")
    val date = Date(timeStamp.toLong() * 1000)
    return sdf.format(date)
}

// Formats a timestamp to a day string.
fun dayFormat(timeStamp: Int): String {
    val sdf = SimpleDateFormat("EEE")
    val date = Date(timeStamp.toLong() * 1000)
    return sdf.format(date)
}

// Converts a string number to a formatted decimal string.
fun convertDecimal(number: String?): String {
    // Check if the input number is not null and is a valid float
    return number?.toFloatOrNull()?.let {
        val a = it / 1000 // Convert into kilometers
        "%.1f".format(a)
    } ?: "N/A" // Provide a default value or handle the case where the input is null or not a valid float
}
