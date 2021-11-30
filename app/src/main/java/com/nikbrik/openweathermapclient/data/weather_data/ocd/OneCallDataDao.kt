package com.nikbrik.openweathermapclient.data.weather_data.ocd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface OneCallDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneCallData(oneCallData: OneCallDataEntity)

    @Transaction
    @Query("SELECT * FROM ${OneCallDataContract.TABLE_NAME} ORDER BY ${OneCallDataContract.Columns.ID}")
    suspend fun getAllOcd(): List<OneCallDataWithLists>

    @Query("SELECT MAX(${OneCallDataContract.Columns.ID}) FROM ${OneCallDataContract.TABLE_NAME}")
    suspend fun getLastOcdId(): Long?

    @Delete(entity = OneCallDataEntity::class)
    suspend fun delete(ocdEntity: OneCallDataEntity)

    @Query("DELETE FROM ${OneCallDataContract.TABLE_NAME} WHERE ${OneCallDataContract.Columns.ID} = :id")
    suspend fun deleteById(id: Long)
}
