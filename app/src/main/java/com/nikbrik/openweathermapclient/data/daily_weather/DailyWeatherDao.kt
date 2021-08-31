package com.nikbrik.openweathermapclient.data.daily_weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DailyWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<DailyWeatherEntity>)

    @Query("SELECT * FROM ${DailyWeatherContract.TABLE_NAME}")
    suspend fun getAll(): List<DailyWeatherEntity>

    @Query("DELETE FROM ${DailyWeatherContract.TABLE_NAME}")
    suspend fun deleteAll()
}
