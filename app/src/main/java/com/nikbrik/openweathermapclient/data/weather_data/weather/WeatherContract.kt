package com.nikbrik.openweathermapclient.data.weather_data.weather

object WeatherContract {
    const val TABLE_NAME = "weathers"
    object Columns {
        const val DT = "dt"
        const val ID = "id"
        const val MAIN = "main"
        const val DESCRIPTION = "description"
        const val ICON = "icon"
    }
}
