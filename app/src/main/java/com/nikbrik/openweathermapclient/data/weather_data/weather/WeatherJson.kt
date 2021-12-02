package com.nikbrik.openweathermapclient.data.weather_data.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherJson(
    @Json(name = WeatherContract.Columns.ID)
    val id: Long,
    @Json(name = WeatherContract.Columns.MAIN)
    val main: String,
    @Json(name = WeatherContract.Columns.DESCRIPTION)
    val description: String,
    @Json(name = WeatherContract.Columns.ICON)
    val icon: String,
) {
    companion object {
        const val ICON_PATH = "https://openweathermap.org/img/wn/"
        const val ICON_FILE_NAME = "@2x.png"
    }
}
