package com.nikbrik.openweathermapclient.ui.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikbrik.openweathermapclient.data.Repository
import com.nikbrik.openweathermapclient.data.geocoder.Geo
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    private val ocdLiveData = MutableLiveData<List<OneCallData>>()
    val ocdList: LiveData<List<OneCallData>>
        get() = ocdLiveData
    private val errorLiveData = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = errorLiveData

    private var lat = 0.0
    private var lon = 0.0
    fun setCurrentLocation(lat: Double, lon: Double) {
        this.lat = lat
        this.lon = lon
    }

    private val errorHandler =
        CoroutineExceptionHandler { _, throwable ->
            errorLiveData.postValue(throwable)
        }

    fun getStartData() {
        viewModelScope.launch(Dispatchers.Default + errorHandler) {
            ocdLiveData.postValue(repository.getCashedData())
            ocdLiveData.postValue(repository.getAllData(lat, lon))
        }
    }

    fun addNew(value: String, geoCoordinates: Geo) {
        viewModelScope.launch(Dispatchers.Default + errorHandler) {
            repository.add(value, geoCoordinates.lat, geoCoordinates.lon)
            ocdLiveData.postValue(repository.getCashedData())
        }
    }
}
