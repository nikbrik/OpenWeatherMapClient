package com.nikbrik.openweathermapclient.data.daily_weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikbrik.openweathermapclient.DailyTemp
import com.squareup.moshi.JsonClass

@Entity(
    tableName = DailyWeatherContract.TABLE_NAME
)
@JsonClass(generateAdapter = true)
data class DailyWeather(
    @PrimaryKey
    @ColumnInfo(name = DailyWeatherContract.columns.DT)
    val dt: Int,
    @ColumnInfo(name = DailyWeatherContract.columns.TEMP)
    val temp: DailyTemp,
    @ColumnInfo(name = DailyWeatherContract.columns.FEELS_TEMP)
    val feels_like: DailyTemp,
    @ColumnInfo(name = DailyWeatherContract.columns.CLOUDS)
    val clouds: Int,
    @ColumnInfo(name = DailyWeatherContract.columns.WIND_SPEED)
    val wind_speed: Float,
    @ColumnInfo(name = DailyWeatherContract.columns.OCD_ID)
    val parent_id: Long?
//    var weather: List<Weather>
) {
    //    fun toDailyWithWeather(): DailyWithWeather {
//        return DailyWithWeather(this, weather)
//    }
    fun withParentId(id: Long): DailyWeather =
        DailyWeather(dt, temp, feels_like, clouds, wind_speed, id)
}

// data class DailyWithWeather(
//    @Embedded
//    val dailyWeather: DailyWeather,
//    @Relation(
//        parentColumn = DailyWeatherContract.columns.DT,
//        entityColumn = WeatherContract.columns.ID
//    )
//    val weathers: List<Weather>,
// ) {
//    fun toDailyWeather(): DailyWeather {
//        dailyWeather.weather = weathers
//        return dailyWeather
//    }
// }
