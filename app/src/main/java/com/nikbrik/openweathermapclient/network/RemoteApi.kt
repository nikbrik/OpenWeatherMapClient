package com.nikbrik.openweathermapclient.network

import com.nikbrik.openweathermapclient.data.geocoder.Location
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallData
import com.nikbrik.openweathermapclient.network.RemoteModule.Companion.GEOTREE_URL
import com.nikbrik.openweathermapclient.network.RemoteModule.Companion.OPEN_WEATHER_API_KEY
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
    ): OneCallData

    @GET
    suspend fun getAddressByCoordinates(
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Url url: String = GEOTREE_URL,
    ): List<Location>

    @GET
    suspend fun getLocationsByAddress(
        @Query("term") address: String,
        @Url url: String = GEOTREE_URL,
    ): List<Location>
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
        .baseUrl(BASE_URL)
        .addConverterFactory(converterFactory)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideRemoteApi(retrofit: Retrofit): RemoteApi = retrofit.create()

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/"
        const val GEOTREE_URL = "https://api.geotree.ru/address.php"
        const val OPEN_WEATHER_API_KEY = "e6dd82be071efbb0acabc0854d9461f4"
    }
}
