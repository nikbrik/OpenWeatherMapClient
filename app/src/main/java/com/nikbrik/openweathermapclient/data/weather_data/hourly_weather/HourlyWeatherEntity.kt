package com.nikbrik.openweathermapclient.data.weather_data.hourly_weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataContract

@Entity(
    tableName = HourlyWeatherContract.TABLE_NAME,
    primaryKeys = [
        OneCallDataContract.Columns.LATITUDE,
        OneCallDataContract.Columns.LONGITUDE,
        HourlyWeatherContract.Columns.DT,
        HourlyWeatherContract.Columns.CURRENT,
    ],
)
data class HourlyWeatherEntity(
    @ColumnInfo(name = OneCallDataContract.Columns.LATITUDE)
    val lat: Double,
    @ColumnInfo(name = OneCallDataContract.Columns.LONGITUDE)
    val lon: Double,
    @ColumnInfo(name = HourlyWeatherContract.Columns.CURRENT)
    val current: Boolean,
    @ColumnInfo(name = HourlyWeatherContract.Columns.DT)
    val dt: Long,
    @ColumnInfo(name = HourlyWeatherContract.Columns.TEMP)
    val temp: Float,
    @ColumnInfo(name = HourlyWeatherContract.Columns.FEELS)
    val feelsLike: Float,
    @ColumnInfo(name = HourlyWeatherContract.Columns.CLOUDS)
    val clouds: Int,
    @ColumnInfo(name = HourlyWeatherContract.Columns.WIND_SPEED)
    val windSpeed: Float,
    @ColumnInfo(name = HourlyWeatherContract.Columns.WEATHER_ID)
    val weatherId: Long
) {
    companion object {
        fun fromJson(
            lat: Double,
            lon: Double,
            current: Boolean,
            json: HourlyWeatherJson,
            weatherId: Long
        ): HourlyWeatherEntity {
            return json.run {
                HourlyWeatherEntity(
                    lat = lat,
                    lon = lon,
                    current = current,
                    dt = dt,
                    temp = temp,
                    feelsLike = feels_like,
                    clouds = clouds,
                    windSpeed = wind_speed,
                    weatherId = weatherId,
                )
            }
        }
    }
}
