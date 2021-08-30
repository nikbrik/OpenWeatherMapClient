package com.nikbrik.openweathermapclient.network

import com.nikbrik.openweathermapclient.data.ocd.OneCallData
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("/data/2.5/onecall")
    suspend fun oneCallRequest(
        @Query("appid") appid: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String,
        @Query("lang") lang: String,
    ): OneCallData
}
