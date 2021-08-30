package com.nikbrik.openweathermapclient.data.daily_temp

object TempContract {
    const val TABLE_NAME = "temps"
    object columns {
        const val MORNING = "morn"
        const val DAY = "day"
        const val EVENING = "eve"
        const val NIGHT = "night"
        const val MINIMUM = "min"
        const val MAXIMUM = "max"
    }
}
