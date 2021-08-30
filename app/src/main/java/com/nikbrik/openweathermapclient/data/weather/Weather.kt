package com.nikbrik.openweathermapclient.data.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(
    tableName = WeatherContract.TABLE_NAME
)
@JsonClass(generateAdapter = true)
data class Weather(
    @PrimaryKey
    @ColumnInfo(name = WeatherContract.columns.ID)
    val id: Int,
    @ColumnInfo(name = WeatherContract.columns.MAIN)
    val main: String,
    @ColumnInfo(name = WeatherContract.columns.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = WeatherContract.columns.ICON)
    val icon: String,
)
