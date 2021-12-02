package com.nikbrik.openweathermapclient.data.weather_data.daily_weather

import com.nikbrik.openweathermapclient.DailyTempJson
import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherJson
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DailyWeatherJson(
    val dt: Long,
    val temp: DailyTempJson,
    val feels_like: DailyTempJson,
    val clouds: Int,
    val wind_speed: Float,
    var weather: List<WeatherJson>
)
