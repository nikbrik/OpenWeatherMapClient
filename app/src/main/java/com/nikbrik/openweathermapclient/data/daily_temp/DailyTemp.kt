package com.nikbrik.openweathermapclient

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.nikbrik.openweathermapclient.data.daily_temp.TempContract
import com.squareup.moshi.JsonClass

@Entity(
    tableName = TempContract.TABLE_NAME
)
@JsonClass(generateAdapter = true)
data class DailyTemp(
    @ColumnInfo(name = TempContract.columns.MORNING)
    val morn: Float,
    @ColumnInfo(name = TempContract.columns.DAY)
    val day: Float,
    @ColumnInfo(name = TempContract.columns.EVENING)
    val eve: Float,
    @ColumnInfo(name = TempContract.columns.NIGHT)
    val night: Float,
    @ColumnInfo(name = TempContract.columns.MINIMUM)
    val min: Float?,
    @ColumnInfo(name = TempContract.columns.MAXIMUM)
    val max: Float?,
)
