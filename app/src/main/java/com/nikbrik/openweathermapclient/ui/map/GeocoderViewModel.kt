package com.nikbrik.openweathermapclient.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikbrik.openweathermapclient.data.Repository
import com.nikbrik.openweathermapclient.data.geocoder.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeocoderViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
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
