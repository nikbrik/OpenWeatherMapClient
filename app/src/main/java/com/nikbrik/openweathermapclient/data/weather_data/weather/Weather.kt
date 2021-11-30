package com.nikbrik.openweathermapclient.data.weather_data.weather

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
) {
    fun entityWithParentId(parent_id: String): WeatherEntity {
        return WeatherEntity(key = 0, id, main, description, icon, parent_id)
    }

    companion object {
        const val ICON_PATH = "https://openweathermap.org/img/wn/"
        const val ICON_FILE_NAME = "@2x.png"
    }
}

@Parcelize
@Entity(
    tableName = WeatherContract.TABLE_NAME,
)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WeatherContract.Columns.KEY)
    val key: Long,
    @ColumnInfo(name = WeatherContract.Columns.ID)
    val id: Int,
    @ColumnInfo(name = WeatherContract.Columns.MAIN)
    val main: String,
    @ColumnInfo(name = WeatherContract.Columns.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = WeatherContract.Columns.ICON)
    val icon: String,
    @ColumnInfo(name = WeatherContract.Columns.PARENT_ID)
    var parent_id: String?
) : Parcelable
