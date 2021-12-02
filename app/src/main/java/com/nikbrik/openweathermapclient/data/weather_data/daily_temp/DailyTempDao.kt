package com.nikbrik.openweathermapclient.data.weather_data.daily_temp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikbrik.openweathermapclient.DailyTempEntity

@Dao
interface DailyTempDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<DailyTempEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(temp: DailyTempEntity): Long

    @Query("SELECT * FROM ${DailyTempContract.TABLE_NAME}")
    suspend fun selectAll(): List<DailyTempEntity>

    @Query("DELETE FROM ${DailyTempContract.TABLE_NAME}")
    suspend fun deleteAll()
}
