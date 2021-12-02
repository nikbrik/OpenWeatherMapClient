package com.nikbrik.openweathermapclient.data.weather_data.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = WeatherContract.TABLE_NAME,
)
data class WeatherEntity(
    @PrimaryKey
    @ColumnInfo(name = WeatherContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = WeatherContract.Columns.MAIN)
    val main: String,
    @ColumnInfo(name = WeatherContract.Columns.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = WeatherContract.Columns.ICON)
    val icon: String,
) {
    companion object {
        fun fromJson(json: WeatherJson): WeatherEntity {
            return json.run {
                WeatherEntity(
                    id = id,
                    main = main,
                    description = description,
                    icon = icon,
                )
            }
        }
    }
}
