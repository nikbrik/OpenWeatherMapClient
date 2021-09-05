package com.nikbrik.openweathermapclient.data.geocoder

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Location(
    val value: String,
    val type: String,
    val description: String,
    val address: String?,
    val geo_center: Geo?,
    val geo_inside: Geo?,
    val levels: Levels,
) : Parcelable {
    fun getCoordinates(): Geo {
        return geo_inside
            ?: when {
                levels.level4 != null -> levels.level4.geo_inside
                levels.level3 != null -> levels.level3.geo_inside
                levels.level2 != null -> levels.level2.geo_inside
                levels.level1 != null -> levels.level1.geo_inside
                else -> Geo(0F, 0F)
            }
    }
}
