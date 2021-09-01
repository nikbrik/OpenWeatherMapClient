package com.nikbrik.openweathermapclient.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Networking {
    const val openWeatherApiKey = "e6dd82be071efbb0acabc0854d9461f4"
    private val okHttpClient = OkHttpClient.Builder()
        .build()
    private val retrofitOpenWeather = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()
    val openWeatherApi: OpenWeatherApi
        get() = retrofitOpenWeather.create()
    private val retrofitGeotree = Retrofit.Builder()
        .baseUrl("https://api.geotree.ru/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()
    val geotreeApi: GeotreeApi
        get() = retrofitGeotree.create()
}
