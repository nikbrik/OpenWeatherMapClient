package com.nikbrik.openweathermapclient.data.weather

object WeatherContract {
    const val TABLE_NAME = "weathers"
    object columns {
        const val ID = "id"
        const val MAIN = "main"
        const val DESCRIPTION = "description"
        const val ICON = "icon"
    }
}
