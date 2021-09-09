package com.nikbrik.openweathermapclient.data.weather_data.hourly_weather

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.nikbrik.openweathermapclient.data.weather_data.weather.Weather
import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherContract
import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherEntity
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class HourlyWeather(
    val dt: Long,
    val temp: Float,
    val feels_like: Float,
    val clouds: Int,
    val wind_speed: Float,
    var weather: List<Weather>
) {
    fun entityWithParentId(id: Long) =
        HourlyWeatherEntity(id.toString() + dt.toString(), dt, temp, feels_like, clouds, wind_speed, id)
}

@Parcelize
@Entity(
    tableName = HourlyWeatherContract.TABLE_NAME,
    primaryKeys = [
        HourlyWeatherContract.columns.ID,
    ],
)
data class HourlyWeatherEntity(
    @ColumnInfo(name = HourlyWeatherContract.columns.ID)
    val id: String,
    @ColumnInfo(name = HourlyWeatherContract.columns.DT)
    val dt: Long,
    @ColumnInfo(name = HourlyWeatherContract.columns.TEMP)
    val temp: Float,
    @ColumnInfo(name = HourlyWeatherContract.columns.FEELS)
    val feels_like: Float,
    @ColumnInfo(name = HourlyWeatherContract.columns.CLOUDS)
    val clouds: Int,
    @ColumnInfo(name = HourlyWeatherContract.columns.WIND_SPEED)
    val wind_speed: Float,
    @ColumnInfo(name = HourlyWeatherContract.columns.OCD_ID)
    val parent_id: Long,
) : Parcelable

@Parcelize
data class HourlyWeatherWithLists(
    @Embedded
    val entity: HourlyWeatherEntity,
    @Relation(
        parentColumn = HourlyWeatherContract.columns.ID,
        entityColumn = WeatherContract.columns.PARENT_ID
    )
    val weatherList: List<WeatherEntity>,
) : Parcelable
