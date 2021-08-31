package com.nikbrik.openweathermapclient.data

import com.nikbrik.openweathermapclient.data.ocd.OneCallData
import com.nikbrik.openweathermapclient.data.ocd.OneCallDataWithLists
import com.nikbrik.openweathermapclient.network.Networking

class WeatherRepository {

    private val ocdDao
        get() = Database.instance.ocdDao()
    private val hourlyWeatherDao
        get() = Database.instance.hourlyWeatherDao()
    private val dailyWeatherDao
        get() = Database.instance.dailyWeatherDao()
    private val dailyTempDao
        get() = Database.instance.dailyTempDao()
    private val weatherDao
        get() = Database.instance.weatherDao()

    suspend fun getCashedData(): List<OneCallDataWithLists> {
        return Database.instance.ocdDao().getAllOcd()
    }

    suspend fun getData(): List<OneCallDataWithLists> {

        // Верхняя в списке всегда запись о текущем местоположении
        val currentLocationId = 0L

        // Удалить запись о текущем местоположении
        ocdDao.deleteById(currentLocationId)

        // Полностью очистить детализированные таблицы,
        // ocd оставляем, т.к. данные нужны для повторого запроса
        hourlyWeatherDao.deleteAll()
        dailyWeatherDao.deleteAll()
        dailyTempDao.deleteAll()
        weatherDao.deleteAll()

        // Повторный запрос к АПИ
        ocdDao.getAllOcd().forEach { ocdJson ->
            val ocd = oneCallDataFromApi(
                ocdJson.entity.lat.toString(),
                ocdJson.entity.lon.toString(),
                ocdJson.entity.id!!
            )
            saveOcdToDB(ocd)
        }

        // Создание записи о текущем местоположении
        val currentOcd = oneCallDataFromApi(
            "10",
            "10",
            currentLocationId,
        )
        currentOcd.entity.apply { isCurrent = true }
        saveOcdToDB(currentOcd)

        return getCashedData()
    }

    private suspend fun saveOcdToDB(ocd: OneCallData) {
        ocdDao.insertOneCallData(ocd.entity)
        saveHourlyWeatherDataFromOcd(ocd)
        saveDailyWeatherDataFromOcd(ocd)
    }

    private suspend fun saveHourlyWeatherDataFromOcd(ocd: OneCallData) {
        hourlyWeatherDao.insertList(
            ocd.hourly.map { hourlyWeather ->

                // Сохранить объекты погоды
                weatherDao.insertList(
                    hourlyWeather.weather.map {
                        // Преобразование в запись БД и передача родительского ид
                        it.entityWithParentId(hourlyWeather.dt)
                    }
                )

                // Получение записи с ид
                hourlyWeather.entityWithParentId(ocd.entity.id ?: 0)
            }
        )
    }

    private suspend fun saveDailyWeatherDataFromOcd(ocd: OneCallData) {
        dailyWeatherDao.insertList(
            ocd.daily.map { dailyWeather ->
                // Сохранить объекты температуры
                dailyTempDao.insertList(
                    listOf(
                        dailyWeather.temp.apply { parent_id = dailyWeather.dt },
                        dailyWeather.feels_like.apply { parent_id = dailyWeather.dt }
                    )
                )

                // Сохранить объекты погоды
                weatherDao.insertList(
                    dailyWeather.weather.map {
                        // Преобразование в запись БД и передача родительского ид
                        it.entityWithParentId(dailyWeather.dt)
                    }
                )

                // Получение записи с id
                dailyWeather.entityWithParentId(ocd.entity.id ?: 0)
            }
        )
    }

    private suspend fun oneCallDataFromApi(
        lat: String,
        lon: String,
        setId: Long,
    ): OneCallData {
        return Networking.api.oneCallRequest(
            appid = "e6dd82be071efbb0acabc0854d9461f4",
            lat = lat,
            lon = lon,
            exclude = "current, minutely",
            lang = "ru",
        ).apply {
            this.id = setId
        }
    }
}
