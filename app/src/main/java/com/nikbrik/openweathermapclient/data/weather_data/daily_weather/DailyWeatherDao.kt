package com.nikbrik.openweathermapclient.data.weather_data.daily_weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DailyWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<DailyWeatherEntity>)

    @Query("SELECT * FROM ${DailyWeatherContract.TABLE_NAME}")
    suspend fun selectAll(): List<DailyWeatherEntity>

    @Query("DELETE FROM ${DailyWeatherContract.TABLE_NAME}")
    suspend fun deleteAll()
}
