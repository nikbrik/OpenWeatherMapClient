package com.nikbrik.openweathermapclient.data.geocoder

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class LevelDescription(
    val value: String,
    val description: String,
    val geo_center: Geo,
    val geo_inside: Geo,
) : Parcelable
