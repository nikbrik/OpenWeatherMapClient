package com.nikbrik.openweathermapclient

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikbrik.openweathermapclient.data.weather_data.daily_temp.DailyTempContract
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = DailyTempContract.TABLE_NAME
)
@JsonClass(generateAdapter = true)
data class DailyTemp(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DailyTempContract.columns.ID)
    val id: Long?,
    @ColumnInfo(name = DailyTempContract.columns.MORNING)
    val morn: Float,
    @ColumnInfo(name = DailyTempContract.columns.DAY)
    val day: Float,
    @ColumnInfo(name = DailyTempContract.columns.EVENING)
    val eve: Float,
    @ColumnInfo(name = DailyTempContract.columns.NIGHT)
    val night: Float,
    @ColumnInfo(name = DailyTempContract.columns.MINIMUM)
    val min: Float?,
    @ColumnInfo(name = DailyTempContract.columns.MAXIMUM)
    val max: Float?,
    @ColumnInfo(name = DailyTempContract.columns.PARENT_ID)
    var parent_id: Long?
) : Parcelable
