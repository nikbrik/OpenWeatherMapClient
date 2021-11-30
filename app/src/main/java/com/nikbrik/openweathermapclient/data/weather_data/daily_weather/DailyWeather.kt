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
    val dt: Long,
    val temp: DailyTemp,
    val feels_like: DailyTemp,
    val clouds: Int,
    val wind_speed: Float,
    var weather: List<Weather>
) {
    fun entityWithParentId(id: Long) =
        DailyWeatherEntity(id.toString() + dt.toString(), dt, clouds, wind_speed, id)
}

@Parcelize
@Entity(
    tableName = DailyWeatherContract.TABLE_NAME,
    primaryKeys = [
        DailyWeatherContract.Columns.ID,
    ],
)
data class DailyWeatherEntity(
    @ColumnInfo(name = DailyWeatherContract.Columns.ID)
    val id: String,
    @ColumnInfo(name = DailyWeatherContract.Columns.DT)
    val dt: Long,
    @ColumnInfo(name = DailyWeatherContract.Columns.CLOUDS)
    val clouds: Int,
    @ColumnInfo(name = DailyWeatherContract.Columns.WIND_SPEED)
    val wind_speed: Float,
    @ColumnInfo(name = DailyWeatherContract.Columns.OCD_ID)
    val parent_id: Long,
) : Parcelable

@Parcelize
data class DailyWeatherWithLists(
    @Embedded
    val entity: DailyWeatherEntity,
    @Relation(
        parentColumn = DailyWeatherContract.Columns.ID,
        entityColumn = DailyTempContract.Columns.PARENT_ID,
    )
    val temperatureList: List<DailyTemp>,
    @Relation(
        parentColumn = DailyWeatherContract.Columns.ID,
        entityColumn = WeatherContract.Columns.PARENT_ID
    )
    val weatherList: List<WeatherEntity>,
) : Parcelable
