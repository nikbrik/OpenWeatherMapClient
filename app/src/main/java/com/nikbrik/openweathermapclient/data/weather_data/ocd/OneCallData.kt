package com.nikbrik.openweathermapclient.data.weather_data.ocd

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeather
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeatherContract
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeatherEntity
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeatherWithLists
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeather
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeatherContract
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeatherEntity
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeatherWithLists
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class OneCallData(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val current: HourlyWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>,
) {
    val entity: OneCallDataEntity
        get() = OneCallDataEntity(lat, lon, timezone, timezone_offset)
}

@Parcelize
@Entity(
    tableName = OneCallDataContract.TABLE_NAME,
    primaryKeys = [
        OneCallDataContract.Columns.LATITUDE,
        OneCallDataContract.Columns.LONGITUDE,
    ],
)
data class OneCallDataEntity(
    @ColumnInfo(name = OneCallDataContract.Columns.LATITUDE)
    val lat: Double,
    @ColumnInfo(name = OneCallDataContract.Columns.LONGITUDE)
    val lon: Double,
    @ColumnInfo(name = OneCallDataContract.Columns.TIMEZONE)
    val timezone: String,
    @ColumnInfo(name = OneCallDataContract.Columns.TIMEZONE_OFFSET)
    val timezone_offset: Int,
    @ColumnInfo(name = OneCallDataContract.Columns.NAME)
    var name: String = "",
    @ColumnInfo(name = OneCallDataContract.Columns.ID)
    var id: Long = 0L,
) : Parcelable

@Parcelize
data class OneCallDataWithLists(
    @Embedded
    val entity: OneCallDataEntity,

    @Relation(
        entity = HourlyWeatherEntity::class,
        parentColumn = OneCallDataContract.Columns.ID,
        entityColumn = HourlyWeatherContract.Columns.OCD_ID,
    )
    val current: List<HourlyWeatherWithLists>,

    @Relation(
        entity = HourlyWeatherEntity::class,
        parentColumn = OneCallDataContract.Columns.ID,
        entityColumn = HourlyWeatherContract.Columns.OCD_ID,
    )
    val hourly: List<HourlyWeatherWithLists>,

    @Relation(
        entity = DailyWeatherEntity::class,
        parentColumn = OneCallDataContract.Columns.ID,
        entityColumn = DailyWeatherContract.Columns.OCD_ID,
    )
    val daily: List<DailyWeatherWithLists>,
) : Parcelable
