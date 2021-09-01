package com.nikbrik.openweathermapclient.network

import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallData
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("/data/2.5/onecall")
    suspend fun oneCallRequest(
        @Query("appid") appid: String,
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("exclude") exclude: String,
        @Query("lang") lang: String,
    ): OneCallData
}
