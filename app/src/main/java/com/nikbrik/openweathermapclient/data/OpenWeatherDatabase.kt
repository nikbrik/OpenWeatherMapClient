package com.nikbrik.openweathermapclient.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikbrik.openweathermapclient.data.OpenWeatherDatabase.Companion.DB_VERSION
import com.nikbrik.openweathermapclient.data.hourly_weather.HourlyWeatherDao
import com.nikbrik.openweathermapclient.data.hourly_weather.HourlyWeatherEntity
import com.nikbrik.openweathermapclient.data.ocd.OneCallDataDao
import com.nikbrik.openweathermapclient.data.ocd.OneCallDataEntity

@Database(
    entities = [
        OneCallDataEntity::class,
        HourlyWeatherEntity::class,
    ],
    version = DB_VERSION,
)
abstract class OpenWeatherDatabase : RoomDatabase() {

    //    abstract fun weatherDao(): WeatherDao
//    abstract fun dailyWeatherDao(): DailyWeatherDao
    abstract fun ocdDao(): OneCallDataDao
    abstract fun hourlyWeatherDao(): HourlyWeatherDao

    companion object {
        const val DATABASE_NAME = "openweatherdb"
        const val DB_VERSION = 5
    }
}
