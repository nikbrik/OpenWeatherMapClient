package com.nikbrik.openweathermapclient.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikbrik.openweathermapclient.data.Repository
import com.nikbrik.openweathermapclient.data.geocoder.Location
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GeocoderViewModel : ViewModel() {
    private val repository = Repository()
    private val locationLiveData = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>>
        get() = locationLiveData
    private val errorLiveData = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = errorLiveData

    private val errorHandler =
        CoroutineExceptionHandler { _, throwable ->
            errorLiveData.postValue(throwable)
        }

    fun searchLocation(text: String) {
        viewModelScope.launch(Dispatchers.Default + errorHandler) {
            val searchResultList = repository.getLocationsByAddress(text)
            locationLiveData.postValue(searchResultList)
        }
    }
}
