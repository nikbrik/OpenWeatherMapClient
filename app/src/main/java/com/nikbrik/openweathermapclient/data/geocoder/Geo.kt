package com.nikbrik.openweathermapclient.data.geocoder

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Geo(
    val lon: Float,
    val lat: Float,
)
