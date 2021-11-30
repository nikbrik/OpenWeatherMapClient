package com.nikbrik.openweathermapclient.data.weather_data.weather

object WeatherContract {
    const val TABLE_NAME = "weathers"
    object Columns {
        const val KEY = "key"
        const val ID = "id"
        const val MAIN = "main"
        const val DESCRIPTION = "description"
        const val ICON = "icon"
        const val PARENT_ID = "parent_id"
        const val OCD_ID = "ocd_id"
    }
}
