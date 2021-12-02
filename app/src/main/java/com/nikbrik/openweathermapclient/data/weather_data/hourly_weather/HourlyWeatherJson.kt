package com.nikbrik.openweathermapclient.data.weather_data.hourly_weather

import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherJson
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HourlyWeatherJson(
    val dt: Long,
    val temp: Float,
    val feels_like: Float,
    val clouds: Int,
    val wind_speed: Float,
    var weather: List<WeatherJson>,
    var current: Boolean?
)
