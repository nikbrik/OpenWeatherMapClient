package com.nikbrik.openweathermapclient.data.weather_data.daily_weather

import android.os.Parcelable
import com.nikbrik.openweathermapclient.DailyTemp
import com.nikbrik.openweathermapclient.data.weather_data.weather.Weather
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyWeather(
    val dt: Long,
    val temp: DailyTemp,
    val feels_like: DailyTemp,
    val clouds: Int,
    val wind_speed: Float,
    val weather: Weather,
) : Parcelable {
    companion object {
        fun fromEntity(
            entity: DailyWeatherEntity,
            temp: DailyTemp,
            feelsLikeTemp: DailyTemp,
            weather: Weather
        ): DailyWeather {
            return DailyWeather(
                dt = entity.dt,
                temp = temp,
                feels_like = feelsLikeTemp,
                entity.clouds,
                entity.windSpeed,
                weather = weather,
            )
        }
    }
}
