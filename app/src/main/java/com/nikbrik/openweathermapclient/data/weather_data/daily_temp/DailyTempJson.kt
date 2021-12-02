package com.nikbrik.openweathermapclient

import androidx.room.ColumnInfo
import com.nikbrik.openweathermapclient.data.weather_data.daily_temp.DailyTempContract
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DailyTempJson(
    @ColumnInfo(name = DailyTempContract.Columns.MORNING)
    val morn: Float,
    @ColumnInfo(name = DailyTempContract.Columns.DAY)
    val day: Float,
    @ColumnInfo(name = DailyTempContract.Columns.EVENING)
    val eve: Float,
    @ColumnInfo(name = DailyTempContract.Columns.NIGHT)
    val night: Float,
    @ColumnInfo(name = DailyTempContract.Columns.MINIMUM)
    val min: Float?,
    @ColumnInfo(name = DailyTempContract.Columns.MAXIMUM)
    val max: Float?,
)
