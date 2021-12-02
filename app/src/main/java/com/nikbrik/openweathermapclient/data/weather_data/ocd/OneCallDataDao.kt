package com.nikbrik.openweathermapclient.data.weather_data.ocd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataEntity.Companion.CURRENT_NAME

@Dao
interface OneCallDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(oneCallData: OneCallDataEntity)

    @Query("SELECT * FROM ${OneCallDataContract.TABLE_NAME}")
    suspend fun selectAllEntities(): List<OneCallDataEntity>

    @Query("SELECT ${OneCallDataContract.Columns.NAME}, ${OneCallDataContract.Columns.LATITUDE}, ${OneCallDataContract.Columns.LONGITUDE} FROM ${OneCallDataContract.TABLE_NAME} WHERE ${OneCallDataContract.Columns.NAME} != '$CURRENT_NAME'")
    suspend fun selectLocationInfo(): List<LocationInfo>

    @Query("DELETE FROM ${OneCallDataContract.TABLE_NAME}")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(ocdEntity: OneCallDataEntity)
}
