package com.nikbrik.openweathermapclient.data.weather_data.ocd

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = OneCallDataContract.TABLE_NAME,
    primaryKeys = [
        OneCallDataContract.Columns.LATITUDE,
        OneCallDataContract.Columns.LONGITUDE,
    ],
)
data class OneCallDataEntity(
    @ColumnInfo(name = OneCallDataContract.Columns.NAME)
    var name: String,
    @ColumnInfo(name = OneCallDataContract.Columns.LATITUDE)
    val lat: Double,
    @ColumnInfo(name = OneCallDataContract.Columns.LONGITUDE)
    val lon: Double,
    @ColumnInfo(name = OneCallDataContract.Columns.TIMEZONE)
    val timezone: String,
    @ColumnInfo(name = OneCallDataContract.Columns.TIMEZONE_OFFSET)
    val timezoneOffset: Int,
    @ColumnInfo(name = OneCallDataContract.Columns.CURRENT_DT)
    val currentDt: Long,
) {
    companion object {
        const val CURRENT_NAME = "CURRENT"

        fun fromJson(name: String, json: OneCallDataJson): OneCallDataEntity {
            return json.run {
                OneCallDataEntity(
                    name = name,
                    lat = lat,
                    lon = lon,
                    timezone = timezone,
                    timezoneOffset = timezone_offset,
                    currentDt = current.dt,
                )
            }
        }
    }
}
