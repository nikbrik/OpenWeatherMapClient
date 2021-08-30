package com.nikbrik.openweathermapclient.data

import com.nikbrik.openweathermapclient.data.ocd.OneCallData
import com.nikbrik.openweathermapclient.network.Networking

class WeatherRepository {
    suspend fun getData(): List<OneCallData> {
        val ocdDao = Database.instance.ocdDao()
        val hourlyWeatherDao = Database.instance.hourlyWeatherDao()
        ocdDao.getAllOcd().forEach {
            if (it.entity.isCurrent) {
                ocdDao.delete(it.entity)
            } else {
                val ocd = oneCallDataFromApi(
                    it.entity.lat.toString(),
                    it.entity.lon.toString(),
                    it.entity.id!!
                )
                val entity = ocd.entity
                ocdDao.insertOneCallData(entity)
                hourlyWeatherDao.insertList(
                    ocd.hourly.map {
                        it.entityWithParentId(
                            entity.id ?: 0
                        )
                    }
                )
            }
        }
        val currentOcd = oneCallDataFromApi(
            "10",
            "10",
            0
        )
        currentOcd.entity.apply { isCurrent = true }
        val entity = currentOcd.entity
        ocdDao.insertOneCallData(entity)
        hourlyWeatherDao.insertList(
            currentOcd.hourly.map {
                it.entityWithParentId(
                    entity.id ?: 0
                )
            }
        )

        return ocdDao.getAllOcd().map { it.OneCallData }
    }

    private suspend fun oneCallDataFromApi(
        lat: String,
        lon: String,
        id: Long,
    ): OneCallData {
        return Networking.api.oneCallRequest(
            appid = "e6dd82be071efbb0acabc0854d9461f4",
            lat = lat,
            lon = lon,
            exclude = "current, minutely",
            lang = "ru",
        ).apply {
            this.id = id
        }
    }

    suspend fun getCashedData(): List<OneCallData> {
        return Database.instance.ocdDao().getAllOcd().map { it.OneCallData }
    }
}
