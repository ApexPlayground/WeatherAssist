package com.example.weatherassist.data

class DataOrException<T, Boolean, Exception>(
    var data: T? = null,          // The data result
    var isLoading: Boolean? = null, // The loading state
    val exception: Exception? = null // The exception that might occur
)
