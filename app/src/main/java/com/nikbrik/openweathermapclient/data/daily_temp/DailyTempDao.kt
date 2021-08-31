package com.nikbrik.openweathermapclient.data.daily_temp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikbrik.openweathermapclient.DailyTemp

@Dao
interface DailyTempDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<DailyTemp>)

    @Query("SELECT * FROM ${DailyTempContract.TABLE_NAME}")
    suspend fun getAll(): List<DailyTemp>

    @Query("DELETE FROM ${DailyTempContract.TABLE_NAME}")
    suspend fun deleteAll()
}
