package com.nikbrik.openweathermapclient.data.weather_data.daily_temp

object DailyTempContract {
    const val TABLE_NAME = "temperature"
    object Columns {
        const val ID = "id"
        const val MORNING = "morn"
        const val DAY = "day"
        const val EVENING = "eve"
        const val NIGHT = "night"
        const val MINIMUM = "min"
        const val MAXIMUM = "max"
        const val PARENT_ID = "parent_id"
    }
}
