package com.nikbrik.openweathermapclient.network

import com.nikbrik.openweathermapclient.data.geocoder.Location
import retrofit2.http.GET
import retrofit2.http.Query

interface GeotreeApi {
    @GET("/address.php")
    suspend fun getAddressByCoordinates(
        @Query("lon") longitude: Float,
        @Query("lat") latitude: Float,
    ): List<Location>

    @GET("/address.php")
    suspend fun getLocationsByAddress(
        @Query("term") address: String,
    ): List<Location>
}