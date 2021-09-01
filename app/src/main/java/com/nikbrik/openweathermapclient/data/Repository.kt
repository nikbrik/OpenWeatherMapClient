package com.nikbrik.openweathermapclient.data

import com.nikbrik.openweathermapclient.data.geocoder.Location
import com.nikbrik.openweathermapclient.data.weather_data.Database
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeather
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeather
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallData
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataEntity
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataWithLists
import com.nikbrik.openweathermapclient.network.Networking

class Repository {

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
        ocdDao.getAllOcd().forEach { ocdRelations ->
            val ocdJson = oneCallDataFromApi(
                ocdRelations.entity.lat,
                ocdRelations.entity.lon,
            )
            val entity = ocdJson.entity.apply {
                id = ocdRelations.entity.id
                name = ocdRelations.entity.name
            }
            saveOcdToDB(entity, ocdJson.hourly, ocdJson.daily)
        }

        // Создание записи о текущем местоположении
        val currentOcd = oneCallDataFromApi(lat = 10.0F, lon = 10.0F)
        saveOcdToDB(
            currentOcd.entity.apply {
                id = currentLocationId
            },
            currentOcd.hourly,
            currentOcd.daily
        )

        return getCashedData()
    }

    private suspend fun saveOcdToDB(
        ocdEntity: OneCallDataEntity,
        hourly: List<HourlyWeather>,
        daily: List<DailyWeather>,
    ) {
        ocdDao.insertOneCallData(ocdEntity)
        saveHourlyWeatherDataFromOcd(ocdEntity, hourly)
        saveDailyWeatherDataFromOcd(ocdEntity, daily)
    }

    private suspend fun saveHourlyWeatherDataFromOcd(
        ocdEntity: OneCallDataEntity,
        hourly: List<HourlyWeather>,
    ) {
        hourlyWeatherDao.insertList(
            hourly.map { hourlyWeather ->

                // Сохранить объекты погоды
                weatherDao.insertList(
                    hourlyWeather.weather.map {
                        // Преобразование в запись БД и передача родительского ид
                        it.entityWithParentId(hourlyWeather.dt)
                    }
                )

                // Получение записи с ид
                hourlyWeather.entityWithParentId(ocdEntity.id)
            }
        )
    }

    private suspend fun saveDailyWeatherDataFromOcd(
        ocdEntity: OneCallDataEntity,
        daily: List<DailyWeather>,
    ) {
        dailyWeatherDao.insertList(
            daily.map { dailyWeather ->
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
                dailyWeather.entityWithParentId(ocdEntity.id)
            }
        )
    }

    private suspend fun oneCallDataFromApi(
        lat: Float,
        lon: Float,
    ): OneCallData {
        return Networking.openWeatherApi.oneCallRequest(
            appid = Networking.openWeatherApiKey,
            lat = lat,
            lon = lon,
            exclude = "current, minutely",
            lang = "ru",
        )
    }

    private suspend fun getAddressByCoordinates(lon: Float, lat: Float): String {
        val locations = Networking.geotreeApi.getAddressByCoordinates(lon, lat)
        return locations.firstOrNull()?.value ?: ""
    }

    private suspend fun getLocationsByAddress(address: String): List<Location> {
        return Networking.geotreeApi.getLocationsByAddress(address)
    }
}
