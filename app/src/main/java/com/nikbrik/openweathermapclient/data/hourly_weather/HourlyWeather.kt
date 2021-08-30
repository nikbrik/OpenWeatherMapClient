package com.nikbrik.openweathermapclient.data.hourly_weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikbrik.openweathermapclient.data.weather.Weather
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HourlyWeather(
    val dt: Int,
    val temp: Float,
    val feels_like: Float,
    val clouds: Int,
    val wind_speed: Float,
    var weather: List<Weather>
) {
    fun entityWithParentId(id: Long) =
        HourlyWeatherEntity(dt, temp, feels_like, clouds, wind_speed, id)
}

@Entity(tableName = HourlyWeatherContract.TABLE_NAME)
data class HourlyWeatherEntity(
    @PrimaryKey
    @ColumnInfo(name = HourlyWeatherContract.columns.DT)
    val dt: Int,
    @ColumnInfo(name = HourlyWeatherContract.columns.TEMP)
    val temp: Float,
    @ColumnInfo(name = HourlyWeatherContract.columns.FEELS)
    val feels_like: Float,
    @ColumnInfo(name = HourlyWeatherContract.columns.CLOUDS)
    val clouds: Int,
    @ColumnInfo(name = HourlyWeatherContract.columns.WIND_SPEED)
    val wind_speed: Float,
    @ColumnInfo(name = HourlyWeatherContract.columns.OCD_ID)
    val parent_id: Long?
) {
    val hourlyWeather: HourlyWeather
        get() = HourlyWeather(dt, temp, feels_like, clouds, wind_speed, emptyList())

    fun withParentId(id: Long): HourlyWeatherEntity =
        HourlyWeatherEntity(dt, temp, feels_like, clouds, wind_speed, id)
}

// data class HourlyWithWeather(
//    @Embedded
//    val hourlyWeather: HourlyWeather,
//    @Relation(
//        parentColumn = HourlyWeatherContract.columns.DT,
//        entityColumn = WeatherContract.columns.ID
//    )
//    val weathers: List<Weather>,
// ) {
//    fun toHourlyWeather(): HourlyWeather {
//        hourlyWeather.weather = weathers
//        return hourlyWeather
//    }
// }
