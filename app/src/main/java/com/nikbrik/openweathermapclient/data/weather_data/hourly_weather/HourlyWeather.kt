package com.nikbrik.openweathermapclient.data.weather_data.hourly_weather

import android.os.Parcelable
import com.nikbrik.openweathermapclient.data.weather_data.weather.Weather
import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class HourlyWeather(
    val dt: Long,
    val temp: Float,
    val feels_like: Float,
    val clouds: Int,
    val wind_speed: Float,
    val weather: Weather,
) : Parcelable {
    companion object {
        fun fromJson(json: HourlyWeatherJson): HourlyWeather {
            val weather = json.weather.firstOrNull()
            val weatherTitle = weather?.main ?: ""
            val weatherDescription = weather?.description ?: ""
            val weatherIcon = weather?.icon ?: ""
            return HourlyWeather(
                json.dt,
                json.temp,
                json.feels_like,
                json.clouds,
                json.wind_speed,
                Weather(weatherTitle, weatherDescription, weatherIcon),
            )
        }

        fun fromEntities(
            entity: HourlyWeatherEntity,
            weatherList: List<WeatherEntity>
        ): HourlyWeather {
            val weather = weatherList.firstOrNull()
            val weatherTitle = weather?.main ?: ""
            val weatherDescription = weather?.description ?: ""
            val weatherIcon = weather?.icon ?: ""
            return HourlyWeather(
                entity.dt,
                entity.temp,
                entity.feelsLike,
                entity.clouds,
                entity.windSpeed,
                Weather(weatherTitle, weatherDescription, weatherIcon),
            )
        }
    }
}
