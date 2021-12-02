package com.nikbrik.openweathermapclient.data.weather_data.ocd

import android.os.Parcelable
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeather
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeather
import kotlinx.parcelize.Parcelize

@Parcelize
data class OneCallData(
    val name: String,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val current: HourlyWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>,
) : Parcelable
