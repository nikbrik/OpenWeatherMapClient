package com.nikbrik.openweathermapclient.data.ocd

object OneCallDataContract {
    const val TABLE_NAME = "ocd"
    object columns {
        const val ID = "id"
        const val LATITUDE = "lat"
        const val LONGITUDE = "lon"
        const val TIMEZONE = "timezone"
        const val TIMEZONE_OFFSET = "timezone_offset"
        const val CURRENT = "current"
    }
}
