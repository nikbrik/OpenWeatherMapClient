package com.nikbrik.openweathermapclient.data.geocoder

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Geo(
    val lon: Double,
    val lat: Double,
) : Parcelable
