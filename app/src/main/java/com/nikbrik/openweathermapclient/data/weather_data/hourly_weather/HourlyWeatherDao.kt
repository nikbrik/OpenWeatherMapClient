package com.nikbrik.openweathermapclient.data.weather_data.hourly_weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HourlyWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<HourlyWeatherEntity>)

    @Query("SELECT * FROM ${HourlyWeatherContract.TABLE_NAME}")
    suspend fun selectAll(): List<HourlyWeatherEntity>

    @Query("DELETE FROM ${HourlyWeatherContract.TABLE_NAME}")
    suspend fun deleteAll()
}
