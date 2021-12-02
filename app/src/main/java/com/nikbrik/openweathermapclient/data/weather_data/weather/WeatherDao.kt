package com.nikbrik.openweathermapclient.data.weather_data.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(set: Set<WeatherEntity>)

    @Query("SELECT * FROM ${WeatherContract.TABLE_NAME}")
    suspend fun selectAll(): List<WeatherEntity>

    @Query("DELETE FROM ${WeatherContract.TABLE_NAME}")
    suspend fun deleteAll()
}
