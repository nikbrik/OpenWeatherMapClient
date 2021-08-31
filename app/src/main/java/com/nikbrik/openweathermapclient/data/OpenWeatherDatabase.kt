package com.nikbrik.openweathermapclient.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikbrik.openweathermapclient.DailyTemp
import com.nikbrik.openweathermapclient.data.OpenWeatherDatabase.Companion.DB_VERSION
import com.nikbrik.openweathermapclient.data.daily_temp.DailyTempDao
import com.nikbrik.openweathermapclient.data.daily_weather.DailyWeatherDao
import com.nikbrik.openweathermapclient.data.daily_weather.DailyWeatherEntity
import com.nikbrik.openweathermapclient.data.hourly_weather.HourlyWeatherDao
import com.nikbrik.openweathermapclient.data.hourly_weather.HourlyWeatherEntity
import com.nikbrik.openweathermapclient.data.ocd.OneCallDataDao
import com.nikbrik.openweathermapclient.data.ocd.OneCallDataEntity
import com.nikbrik.openweathermapclient.data.weather.WeatherDao
import com.nikbrik.openweathermapclient.data.weather.WeatherEntity

@Database(
    entities = [
        OneCallDataEntity::class,
        HourlyWeatherEntity::class,
        DailyWeatherEntity::class,
        DailyTemp::class,
        WeatherEntity::class,
    ],
    version = DB_VERSION,
)
abstract class OpenWeatherDatabase : RoomDatabase() {

    abstract fun ocdDao(): OneCallDataDao
    abstract fun hourlyWeatherDao(): HourlyWeatherDao
    abstract fun dailyWeatherDao(): DailyWeatherDao
    abstract fun dailyTempDao(): DailyTempDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        const val DATABASE_NAME = "openweatherdb"
        const val DB_VERSION = 13
    }
}
