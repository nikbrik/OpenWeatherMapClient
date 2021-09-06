package com.nikbrik.openweathermapclient.data.weather_data.daily_weather

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.nikbrik.openweathermapclient.DailyTemp
import com.nikbrik.openweathermapclient.data.weather_data.daily_temp.DailyTempContract
import com.nikbrik.openweathermapclient.data.weather_data.weather.Weather
import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherContract
import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherEntity
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class DailyWeather(
    val dt: Int,
    val temp: DailyTemp,
    val feels_like: DailyTemp,
    val clouds: Int,
    val wind_speed: Float,
    var weather: List<Weather>
) {
    fun entityWithParentId(id: Long) =
        DailyWeatherEntity(dt, clouds, wind_speed, id)
}

@Parcelize
@Entity(
    tableName = DailyWeatherContract.TABLE_NAME,
    primaryKeys = [
        DailyWeatherContract.columns.DT,
        DailyWeatherContract.columns.OCD_ID,
    ],
)
data class DailyWeatherEntity(
    @ColumnInfo(name = DailyWeatherContract.columns.DT)
    val dt: Int,
    @ColumnInfo(name = DailyWeatherContract.columns.CLOUDS)
    val clouds: Int,
    @ColumnInfo(name = DailyWeatherContract.columns.WIND_SPEED)
    val wind_speed: Float,
    @ColumnInfo(name = DailyWeatherContract.columns.OCD_ID)
    val parent_id: Long
) : Parcelable

@Parcelize
data class DailyWeatherWithLists(
    @Embedded
    val entity: DailyWeatherEntity,
    @Relation(
        parentColumn = DailyWeatherContract.columns.DT,
        entityColumn = DailyTempContract.columns.PARENT_ID,
    )
    val temperatureList: List<DailyTemp>,
    @Relation(
        parentColumn = DailyWeatherContract.columns.DT,
        entityColumn = WeatherContract.columns.PARENT_ID
    )
    val weatherList: List<WeatherEntity>,
) : Parcelable
