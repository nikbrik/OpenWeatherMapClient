package com.nikbrik.openweathermapclient.data.weather_data.ocd

import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeatherJson
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeatherJson
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OneCallDataJson(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val current: HourlyWeatherJson,
    val hourly: List<HourlyWeatherJson>,
    val daily: List<DailyWeatherJson>,
)
