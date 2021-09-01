package com.nikbrik.openweathermapclient.data.geocoder

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Levels(
    @Json(name = "1")
    val level1: LevelDescription?,
    @Json(name = "2")
    val level2: LevelDescription?,
    @Json(name = "3")
    val level3: LevelDescription?,
    @Json(name = "4")
    val level4: LevelDescription?,
)
