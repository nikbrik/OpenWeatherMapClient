package com.nikbrik.openweathermapclient.data.weather_data.daily_weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataContract

@Entity(
    tableName = DailyWeatherContract.TABLE_NAME,
    primaryKeys = [
        OneCallDataContract.Columns.LATITUDE,
        OneCallDataContract.Columns.LONGITUDE,
        DailyWeatherContract.Columns.DT,
    ],
)
data class DailyWeatherEntity(
    @ColumnInfo(name = OneCallDataContract.Columns.LATITUDE)
    val lat: Double,
    @ColumnInfo(name = OneCallDataContract.Columns.LONGITUDE)
    val lon: Double,
    @ColumnInfo(name = DailyWeatherContract.Columns.DT)
    val dt: Long,
    @ColumnInfo(name = DailyWeatherContract.Columns.CLOUDS)
    val clouds: Int,
    @ColumnInfo(name = DailyWeatherContract.Columns.WIND_SPEED)
    val windSpeed: Float,
    @ColumnInfo(name = DailyWeatherContract.Columns.WEATHER_ID)
    val weatherId: Long,
    @ColumnInfo(name = DailyWeatherContract.Columns.TEMP_ID)
    val tempId: Long,
    @ColumnInfo(name = DailyWeatherContract.Columns.FEELS_ID)
    val feelsLikeTempId: Long,
) {
    companion object {
        fun fromJson(
            lat: Double,
            lon: Double,
            json: DailyWeatherJson,
            weatherId: Long,
            tempId: Long,
            feelsLikeTempId: Long,
        ): DailyWeatherEntity {
            return json.run {
                DailyWeatherEntity(
                    lat = lat,
                    lon = lon,
                    dt = dt,
                    clouds = clouds,
                    windSpeed = wind_speed,
                    weatherId = weatherId,
                    tempId = tempId,
                    feelsLikeTempId = feelsLikeTempId,
                )
            }
        }
    }
}
