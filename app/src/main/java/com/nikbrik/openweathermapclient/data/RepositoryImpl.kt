package com.nikbrik.openweathermapclient.data

import com.nikbrik.openweathermapclient.data.geocoder.Location
import com.nikbrik.openweathermapclient.data.weather_data.daily_temp.DailyTempDao
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeather
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeatherDao
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeather
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeatherDao
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallData
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataDao
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataEntity
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataWithLists
import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherDao
import com.nikbrik.openweathermapclient.network.RemoteApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

interface Repository {
    suspend fun addNew(value: String, lat: Double, lon: Double)

    suspend fun getCashedData(): List<OneCallDataWithLists>

    suspend fun getData(lat: Double, lon: Double): List<OneCallDataWithLists>

    suspend fun saveOcdToDB(
        ocdEntity: OneCallDataEntity,
        hourly: List<HourlyWeather>,
        daily: List<DailyWeather>,
    )

    suspend fun saveHourlyWeatherDataFromOcd(
        ocdEntity: OneCallDataEntity,
        hourly: List<HourlyWeather>,
    )

    suspend fun saveDailyWeatherDataFromOcd(
        ocdEntity: OneCallDataEntity,
        daily: List<DailyWeather>,
    )

    suspend fun oneCallDataFromApi(
        lat: Double,
        lon: Double,
    ): OneCallData

    suspend fun getAddressByCoordinates(lon: Double, lat: Double): String

    suspend fun getLocationsByAddress(address: String): List<Location>
}

class RepositoryImpl @Inject constructor(
    private val ocdDao: OneCallDataDao,
    private val hourlyWeatherDao: HourlyWeatherDao,
    private val dailyWeatherDao: DailyWeatherDao,
    private val dailyTempDao: DailyTempDao,
    private val weatherDao: WeatherDao,
    private val remoteApi: RemoteApi,
) : Repository {

    override suspend fun addNew(value: String, lat: Double, lon: Double) {
        val newOcd = oneCallDataFromApi(lat, lon)
        saveOcdToDB(
            newOcd.entity.apply {
                id = (ocdDao.getLastOcdId() ?: 0) + 1
                name = value
            },
            newOcd.hourly,
            newOcd.daily
        )
    }

    override suspend fun getCashedData(): List<OneCallDataWithLists> {
        return ocdDao.getAllOcd()
    }

    override suspend fun getData(lat: Double, lon: Double): List<OneCallDataWithLists> {

        // Повторный запрос к АПИ
        val currentOcd = oneCallDataFromApi(lat, lon)

        val allOcd = ocdDao.getAllOcd()
        val allCashedData = allOcd.map { ocdRelations ->
            if (ocdRelations.entity.id == 0L) {
                null
            } else {
                val ocdJson = oneCallDataFromApi(
                    ocdRelations.entity.lat,
                    ocdRelations.entity.lon,
                )
                val entity = ocdJson.entity.apply {
                    id = ocdRelations.entity.id
                    name = ocdRelations.entity.name
                }

                val map = mutableMapOf<String, Any>()
                map["entity"] = entity
                map["ocdJson"] = ocdJson
                map
            }
        }

        // Полностью очистить детализированные таблицы,
        // ocd оставляем, т.к. данные нужны для повторого запроса
        hourlyWeatherDao.deleteAll()
        dailyWeatherDao.deleteAll()
        dailyTempDao.deleteAll()
        weatherDao.deleteAll()

        allCashedData.forEach {
            if (it != null) {
                val ocdRelation = it["ocdJson"] as OneCallData
                saveOcdToDB(
                    it["entity"] as OneCallDataEntity,
                    ocdRelation.hourly,
                    ocdRelation.daily
                )
            }
        }

        // Верхняя в списке всегда запись о текущем местоположении
        val currentLocationId = 0L

        // Удалить запись о текущем местоположении
        ocdDao.deleteById(currentLocationId)
        // Создание записи о текущем местоположении
        saveOcdToDB(
            currentOcd.entity.apply {
                id = currentLocationId
            },
            currentOcd.hourly,
            currentOcd.daily
        )

        return getCashedData()
    }

    override suspend fun saveOcdToDB(
        ocdEntity: OneCallDataEntity,
        hourly: List<HourlyWeather>,
        daily: List<DailyWeather>,
    ) {
        ocdDao.insertOneCallData(ocdEntity)
        saveHourlyWeatherDataFromOcd(ocdEntity, hourly)
        saveDailyWeatherDataFromOcd(ocdEntity, daily)
    }

    override suspend fun saveHourlyWeatherDataFromOcd(
        ocdEntity: OneCallDataEntity,
        hourly: List<HourlyWeather>,
    ) {
        hourlyWeatherDao.insertList(
            hourly.map { hourlyWeather ->

                // Получение записи с ид
                val hourlyWeatherEntity = hourlyWeather.entityWithParentId(ocdEntity.id)

                // Сохранить объекты погоды
                weatherDao.insertList(
                    hourlyWeather.weather.map {
                        // Преобразование в запись БД и передача родительского ид
                        it.entityWithParentId(hourlyWeatherEntity.id)
                    }
                )

                hourlyWeatherEntity
            }
        )
    }

    override suspend fun saveDailyWeatherDataFromOcd(
        ocdEntity: OneCallDataEntity,
        daily: List<DailyWeather>,
    ) {
        dailyWeatherDao.insertList(
            daily.map { dailyWeather ->

                // Получение записи с id
                val dailyWeatherEntity = dailyWeather.entityWithParentId(ocdEntity.id)

                // Сохранить объекты температуры
                dailyTempDao.insertList(
                    listOf(
                        dailyWeather.temp.apply {
                            parent_id = dailyWeatherEntity.id
                        },
                        dailyWeather.feels_like.apply {
                            parent_id = dailyWeatherEntity.id
                        }
                    )
                )

                // Сохранить объекты погоды
                weatherDao.insertList(
                    dailyWeather.weather.map {
                        // Преобразование в запись БД и передача родительского ид
                        it.entityWithParentId(dailyWeatherEntity.id)
                    }
                )

                dailyWeatherEntity
            }
        )
    }

    override suspend fun oneCallDataFromApi(
        lat: Double,
        lon: Double,
    ): OneCallData {
        return remoteApi.oneCallRequest(
            lat = lat,
            lon = lon,
            exclude = "minutely",
            lang = "ru",
            units = "metric",
        )
    }

    override suspend fun getAddressByCoordinates(lon: Double, lat: Double): String {
        val locations = remoteApi.getAddressByCoordinates(lon, lat)
        return locations.firstOrNull()?.value ?: ""
    }

    override suspend fun getLocationsByAddress(address: String): List<Location> {
        return remoteApi.getLocationsByAddress(address)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}
