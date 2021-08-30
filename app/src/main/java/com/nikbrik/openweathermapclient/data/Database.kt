package com.nikbrik.openweathermapclient.data

import android.content.Context
import androidx.room.Room

object Database {
    lateinit var instance: OpenWeatherDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            OpenWeatherDatabase::class.java,
            OpenWeatherDatabase.DATABASE_NAME,
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
