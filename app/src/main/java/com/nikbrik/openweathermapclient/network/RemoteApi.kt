package com.nikbrik.openweathermapclient.network

import com.nikbrik.openweathermapclient.data.geocoder.Location
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataJson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import javax.inject.Singleton

interface RemoteApi {
    @GET("/data/2.5/onecall")
    suspend fun oneCallRequest(
        @Query("appid") appid: String = OPEN_WEATHER_API_KEY,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("lang") lang: String,
        @Query("units") units: String,
    ): OneCallDataJson

    @GET
    suspend fun getAddressByCoordinates(
        @Url url: String = GEOTREE_URL,
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
    ): List<Location>

    @GET
    suspend fun getLocationsByAddress(
        @Url url: String = GEOTREE_URL,
        @Query("term") address: String,
    ): List<Location>

    companion object {
        private const val OPEN_WEATHER_API_KEY = "e6dd82be071efbb0acabc0854d9461f4"
        private const val GEOTREE_URL = "https://api.geotree.ru/address.php"
    }
}

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    fun provideConverterFactory(): Converter.Factory = MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(converterFactory)
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideRemoteApi(retrofit: Retrofit): RemoteApi = retrofit.create()

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/"
    }
}
