package com.nikbrik.openweathermapclient

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyTemp(
    val morning: Float,
    val day: Float,
    val evening: Float,
    val night: Float,
    val min: Float,
    val max: Float,
) : Parcelable {
    companion object {
        fun fromEntity(entity: DailyTempEntity): DailyTemp {
            return DailyTemp(
                morning = entity.morn,
                day = entity.day,
                evening = entity.eve,
                night = entity.night,
                min = entity.min,
                max = entity.max
            )
        }
    }
}
