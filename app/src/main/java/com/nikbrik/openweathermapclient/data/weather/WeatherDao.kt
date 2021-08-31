package com.nikbrik.openweathermapclient.data.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<WeatherEntity>)

    @Query("SELECT * FROM ${WeatherContract.TABLE_NAME}")
    suspend fun getAll(): List<WeatherEntity>

    @Query("DELETE FROM ${WeatherContract.TABLE_NAME}")
    suspend fun deleteAll()
}
