package com.nikbrik.openweathermapclient.data.geocoder

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    val value: String,
    val type: String,
    val description: String,
    val address: String?,
    val geo_center: Geo?,
    val geo_inside: Geo?,
    val levels: Levels?,
)
