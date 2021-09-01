package com.nikbrik.openweathermapclient.ui.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikbrik.openweathermapclient.data.Repository
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataWithLists
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {
    private val repository = Repository()
    private val oneCallLiveData = MutableLiveData<List<OneCallDataWithLists>>()
    val oneCallData: LiveData<List<OneCallDataWithLists>>
        get() = oneCallLiveData
    private val errorLiveData = MutableLiveData<String>()
    val error: LiveData<String>
        get() = errorLiveData

    private val errorHandler =
        CoroutineExceptionHandler { _, throwable ->
            errorLiveData.postValue(throwable.toString())
        }

    fun getData() {
        viewModelScope.launch(Dispatchers.Default + errorHandler) {
            oneCallLiveData.postValue(repository.getCashedData())
            val data = repository.getData()
            oneCallLiveData.postValue(data)
        }
    }
}