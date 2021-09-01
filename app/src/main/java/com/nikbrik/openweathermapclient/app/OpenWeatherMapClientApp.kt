package com.nikbrik.openweathermapclient.app

import android.app.Application
import com.nikbrik.openweathermapclient.BuildConfig
import com.nikbrik.openweathermapclient.data.weather_data.Database
import timber.log.Timber

class OpenWeatherMapClientApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG && Timber.treeCount() == 0) Timber.plant(Timber.DebugTree())
        Database.init(this)
    }
}
