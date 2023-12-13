package com.example.weatherassist.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherassist.model.Favorite
import com.example.weatherassist.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    // MutableStateFlow to hold the list of favorite items
    private val _favoriteList = MutableStateFlow<List<Favorite>>(emptyList())

    // Exposed as StateFlow to observe changes externally
    val favoriteList = _favoriteList.asStateFlow()

    // Initialization block to fetch the initial list of favorites from the repository
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFavorites().collect { list ->
                if(list.isNotEmpty()){
                    // Update the MutableStateFlow with the fetched list
                    _favoriteList.value = list
                } else {
                    // Log a message if the list of favorites is empty
                    Log.d("FavoriteViewModel", "getAllFavorites(): Empty List of Favorites")
                }
            }
        }
    }

    // Function to insert or update a favorite item in the database
    fun upsertFavorite(favoriteItem: Favorite) = viewModelScope.launch {
        repository.upsertFavorite(favoriteItem)
    }

    // Function to delete a specific favorite item from the database
    fun deleteFavorite(favoriteItem: Favorite) = viewModelScope.launch {
        repository.deleteFavorite(favoriteItem)
    }

}
