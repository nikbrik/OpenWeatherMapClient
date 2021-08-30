package com.nikbrik.openweathermapclient.data.hourly_weather

object HourlyWeatherContract {
    const val TABLE_NAME = "hourly_weather"
    object columns {
        const val DT = "dt"
        const val TEMP = "temp"
        const val FEELS = "feels_like"
        const val CLOUDS = "clouds"
        const val WIND_SPEED = "wind_speed"
        const val OCD_ID = "parent_id"
    }
}
