package com.nikbrik.openweathermapclient.ui.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikbrik.openweathermapclient.data.Repository
import com.nikbrik.openweathermapclient.data.geocoder.Geo
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataWithLists
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StartViewModel : ViewModel() {
    private val repository = Repository()
    private val ocdLiveData = MutableLiveData<List<OneCallDataWithLists>>()
    val ocdList: LiveData<List<OneCallDataWithLists>>
        get() = ocdLiveData
    private val errorLiveData = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = errorLiveData

    private val errorHandler =
        CoroutineExceptionHandler { _, throwable ->
            errorLiveData.postValue(throwable)
        }

    fun getStartData() {
        viewModelScope.launch(Dispatchers.Default + errorHandler) {
            ocdLiveData.postValue(repository.getCashedData())
            ocdLiveData.postValue(repository.getData())
        }
    }

    fun addNew(geoCoordinates: Geo) {
        viewModelScope.launch(Dispatchers.Default + errorHandler) {
            repository.addNew(geoCoordinates.lat, geoCoordinates.lon)
            ocdLiveData.postValue(repository.getCashedData())
        }
    }
}
