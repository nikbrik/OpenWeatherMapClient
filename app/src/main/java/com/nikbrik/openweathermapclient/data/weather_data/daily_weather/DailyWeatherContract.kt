package com.nikbrik.openweathermapclient.data.weather_data.daily_weather

object DailyWeatherContract {
    const val TABLE_NAME = "daily_weathers"
    object Columns {
        const val ID = "id"
        const val DT = "dt"
        const val TEMP = "temp"
        const val FEELS_TEMP = "feels_like"
        const val CLOUDS = "clouds"
        const val WIND_SPEED = "wind_speed"
        const val OCD_ID = "ocd_id"
    }
}
