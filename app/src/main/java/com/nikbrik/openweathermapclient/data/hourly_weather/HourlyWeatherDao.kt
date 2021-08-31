package com.nikbrik.openweathermapclient.data.hourly_weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HourlyWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<HourlyWeatherEntity>)

    @Query("SELECT * FROM ${HourlyWeatherContract.TABLE_NAME}")
    suspend fun getAll(): List<HourlyWeatherEntity>

    @Query("DELETE FROM ${HourlyWeatherContract.TABLE_NAME}")
    suspend fun deleteAll()
}
