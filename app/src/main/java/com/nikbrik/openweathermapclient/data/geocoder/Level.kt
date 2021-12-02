package com.nikbrik.openweathermapclient.data.geocoder

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Levels(
    @Json(name = "1")
    val level1: LevelDescription? = null,
    @Json(name = "2")
    val level2: LevelDescription? = null,
    @Json(name = "3")
    val level3: LevelDescription? = null,
    @Json(name = "4")
    val level4: LevelDescription? = null,
) : Parcelable
