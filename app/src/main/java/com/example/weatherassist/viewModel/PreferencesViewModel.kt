package com.example.weatherassist.viewModel

// Import necessary dependencies
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherassist.pref.UserPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(private val pref: UserPref) : ViewModel() {

    // MutableStateFlow to hold the default city value
    private val _defaultCity = MutableStateFlow<String>("Delhi")
    val defaultCity = _defaultCity.asStateFlow()

    // MutableStateFlow to hold the default unit value
    private val _defaultUnit = MutableStateFlow<String>("")
    val defaultUnit = _defaultUnit.asStateFlow()

    // MutableStateFlow to hold the state of the dynamic theme preference
    private val _isDefaultThemeDynamic = MutableStateFlow(false)
    val isDefaultThemeDynamic = _isDefaultThemeDynamic.asStateFlow()

    init {
        // Collecting default city value from preferences
        viewModelScope.launch {
            pref.getDefaultCity().collect { city ->
                _defaultCity.value = city
            }
        }

        // Collecting default unit value from preferences
        viewModelScope.launch {
            pref.getDefaultUnit().collect { unit ->
                if (unit != "") {
                    _defaultUnit.value = unit
                } else {
                    Log.d("PreferencesViewModel", "Empty default unit")
                }
            }
        }

        // Collecting dynamic theme preference value from preferences
        viewModelScope.launch {
            pref.getIsDynamicTheme().collect { boolean ->
                _isDefaultThemeDynamic.value = boolean
            }
        }
    }

    // Function to save the default city preference
    fun saveDefaultCity(city: String) {
        viewModelScope.launch {
            pref.saveDefaultCity(city)
        }
    }

    // Function to save the dynamic theme preference
    fun saveDynamicTheme(boolean: Boolean) {
        viewModelScope.launch {
            pref.saveDynamicTheme(boolean)
        }
    }

    // Function to save the default unit preference
    fun saveDefaultUnit(unit: String) {
        viewModelScope.launch {
            pref.saveDefaultUnit(unit)
        }
    }
}