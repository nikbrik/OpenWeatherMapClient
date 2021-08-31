package com.nikbrik.openweathermapclient.data.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
) {
    fun entityWithParentId(parent_id: Int): WeatherEntity {
        return WeatherEntity(key = 0, id, main, description, icon, parent_id)
    }
}

@Entity(
    tableName = WeatherContract.TABLE_NAME,
)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WeatherContract.columns.KEY)
    val key: Long,
    @ColumnInfo(name = WeatherContract.columns.ID)
    val id: Int,
    @ColumnInfo(name = WeatherContract.columns.MAIN)
    val main: String,
    @ColumnInfo(name = WeatherContract.columns.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = WeatherContract.columns.ICON)
    val icon: String,
    @ColumnInfo(name = WeatherContract.columns.PARENT_ID)
    var parent_id: Int?
)
