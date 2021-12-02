package com.nikbrik.openweathermapclient.data.weather_data.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val title: String,
    val description: String,
    val icon: String,
) : Parcelable {
    companion object {
        fun fromEntity(entity: WeatherEntity): Weather {
            return entity.run {
                Weather(
                    title = main,
                    description = description,
                    icon = icon,
                )
            }
        }
    }
}
