package com.nikbrik.openweathermapclient.data.geocoder

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LevelDescription(
    val value: String,
    val description: String,
    val geo_center: Geo?,
    val geo_inside: Geo?,
)
