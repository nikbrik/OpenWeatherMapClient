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
    @ColumnInfo(name = DailyTempContract.Columns.ID)
    val id: Long?,
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
    @ColumnInfo(name = DailyTempContract.Columns.PARENT_ID)
    var parent_id: String?,
) : Parcelable
