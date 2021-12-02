package com.nikbrik.openweathermapclient.data

import androidx.room.withTransaction
import com.nikbrik.openweathermapclient.DailyTemp
import com.nikbrik.openweathermapclient.DailyTempEntity
import com.nikbrik.openweathermapclient.data.geocoder.Location
import com.nikbrik.openweathermapclient.data.weather_data.OpenWeatherDatabase
import com.nikbrik.openweathermapclient.data.weather_data.daily_temp.DailyTempDao
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeather
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeatherDao
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeatherEntity
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeather
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeatherDao
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeatherEntity
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallData
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataDao
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataEntity
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataEntity.Companion.CURRENT_NAME
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataJson
import com.nikbrik.openweathermapclient.data.weather_data.weather.Weather
import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherDao
import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherEntity
import com.nikbrik.openweathermapclient.network.RemoteApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

interface Repository {
    suspend fun add(name: String, lat: Double, lon: Double)

    suspend fun getCashedData(): List<OneCallData>

    suspend fun getAllData(currentLat: Double, currentLon: Double): List<OneCallData>

    suspend fun getLocationsByAddress(address: String): List<Location>
}

class RepositoryImpl @Inject constructor(
    // Database
    private val database: OpenWeatherDatabase,
    // DAO
    private val ocdDao: OneCallDataDao,
    private val hourlyWeatherDao: HourlyWeatherDao,
    private val dailyWeatherDao: DailyWeatherDao,
    private val dailyTempDao: DailyTempDao,
    private val weatherDao: WeatherDao,
    // server API
    private val remoteApi: RemoteApi,
) : Repository {

    // +++INTERFACE
    override suspend fun add(name: String, lat: Double, lon: Double) {
        val ocdJson = oneCallDataFromApi(lat, lon)
        database.withTransaction {
            insertOcdFromJsonToDatabase(name, ocdJson)
        }
    }

    override suspend fun getCashedData(): List<OneCallData> {
        val ocdEntities = ocdDao.selectAllEntities()
        val hourlyEntities = hourlyWeatherDao.selectAll()
        val dailyEntities = dailyWeatherDao.selectAll()
        val tempEntities = dailyTempDao.selectAll()
        val weatherEntities = weatherDao.selectAll()

        // Для каждой записи из основной таблицы соберем данные из смежных
        return ocdEntities.map { ocdEntity ->

            // Получение данных по текущей погоде в локации
            val currentEntity = hourlyEntities.find { hourlyEntity ->
                hourlyEntity.current && hourlyEntity.lat == ocdEntity.lat && hourlyEntity.lon == ocdEntity.lon
            }!!
            val current =
                HourlyWeather.fromEntities(
                    currentEntity,
                    weatherEntities.filter { weatherEntity ->
                        weatherEntity.id == currentEntity.weatherId
                    }
                )

            // Получение почасовых данных по локации
            val hourly = hourlyEntities
                .filter { hourlyEntity ->
                    !hourlyEntity.current && hourlyEntity.lat == ocdEntity.lat && hourlyEntity.lon == ocdEntity.lon
                }.map { hourlyEntity ->
                    HourlyWeather.fromEntities(
                        hourlyEntity,
                        weatherEntities.filter { weatherEntity ->
                            weatherEntity.id == hourlyEntity.weatherId
                        }
                    )
                }

            // Получение дневных данных по локации
            val daily = dailyEntities
                .filter { dailyEntity ->
                    dailyEntity.lat == ocdEntity.lat && dailyEntity.lon == ocdEntity.lon
                }.map { dailyEntity ->
                    DailyWeather.fromEntity(
                        dailyEntity,
                        DailyTemp.fromEntity(tempEntities.find { it.id == dailyEntity.tempId }!!),
                        DailyTemp.fromEntity(tempEntities.find { it.id == dailyEntity.feelsLikeTempId }!!),
                        Weather.fromEntity(weatherEntities.find { it.id == dailyEntity.weatherId }!!)
                    )
                }

            // Добавляем в список POJO с данными о погоде
            OneCallData(
                ocdEntity.name,
                ocdEntity.lat,
                ocdEntity.lon,
                ocdEntity.timezone,
                ocdEntity.timezoneOffset,
                current,
                hourly,
                daily,
            )
        }
    }

    // Обновляет данные с сервера, сохраняет их в БД и возвращает результат
    override suspend fun getAllData(currentLat: Double, currentLon: Double): List<OneCallData> {

        // АЛГОРИТМ
        // ВХОДЯЩИЕ ДАННЫЕ: долгота, широта текущей локации для обновления первой записи
        // ИСХОДЯЩИЕ ДАННЫЕ: список POJO OneCallData из базы данных
        // ПОБОЧНЫЕ ЭФФЕКТЫ: очистка БД и запись в БД обновленных данных
        // 1. Получить данные с сервера по всем сохраненным локациям
        // 2. Очистить базу данных
        // 3. Записать серверные данные в БД
        // 4. Вернуть кэшированные данные из БД

        // ШАГ 1.

        val namedJsons = mutableMapOf<String, OneCallDataJson>()

        // Получаем данные о погоде с сервера по местоположению и на текущий момент
        namedJsons[CURRENT_NAME] = oneCallDataFromApi(currentLat, currentLon)

        // Из БД список локаций и данные с сервера по ним
        ocdDao.selectLocationInfo().forEach {
            namedJsons[it.name] = oneCallDataFromApi(it.lat, it.lon)
        }

        // ШАГ 2.

        return database.withTransaction {
            ocdDao.deleteAll()
            hourlyWeatherDao.deleteAll()
            dailyWeatherDao.deleteAll()
            dailyTempDao.deleteAll()
            // weatherDao.deleteAll() - нет смысла вызывать, т.к. это предопределенный набор данных на сервере

            // ШАГ 3.

            namedJsons.forEach {
                insertOcdFromJsonToDatabase(name = it.key, ocdJson = it.value)
            }

            // ШАГ 4.
            getCashedData()
        }
    }

    override suspend fun getLocationsByAddress(address: String): List<Location> {
        return remoteApi.getLocationsByAddress(address = address)
    }
    // ---INTERFACE

    // PRIVATE

    private suspend fun insertOcdFromJsonToDatabase(
        name: String,
        ocdJson: OneCallDataJson
    ) {
        // Запись в БД основной таблицы
        ocdDao.insert(OneCallDataEntity.fromJson(name, ocdJson))

        val weatherSet = mutableSetOf<WeatherEntity>()

        // Сбор и запись почасовых данных
        val hourlyList = mutableListOf<HourlyWeatherEntity>()
        (ocdJson.hourly + ocdJson.current.apply { current = true }).forEach {
            val weather = it.weather.firstOrNull()
            if (weather != null) {
                weatherSet.add(WeatherEntity.fromJson(weather))
            }

            hourlyList.add(
                HourlyWeatherEntity.fromJson(
                    ocdJson.lat,
                    ocdJson.lon,
                    current = it.current ?: false,
                    json = it,
                    weatherId = weather?.id ?: 0L
                )
            )
        }
        hourlyWeatherDao.insert(hourlyList)

        // Сбор и запись дневных данных
        val dailyList = mutableListOf<DailyWeatherEntity>()
        ocdJson.daily.forEach {
            val weather = it.weather.firstOrNull()
            if (weather != null) {
                weatherSet.add(WeatherEntity.fromJson(weather))
            }

            dailyList.add(
                DailyWeatherEntity.fromJson(
                    ocdJson.lat,
                    ocdJson.lon,
                    json = it,
                    weatherId = weather?.id ?: 0L,
                    tempId = dailyTempDao.insert(DailyTempEntity.fromJson(it.temp)), // Вставка в БД
                    feelsLikeTempId = dailyTempDao.insert(DailyTempEntity.fromJson(it.feels_like)), // Вставка в БД
                )
            )
        }
        dailyWeatherDao.insert(dailyList)

        // Пополнение таблицы вариантов погоды
        weatherDao.insert(weatherSet)
    }

    // Получить все данные о погоде в конкретном месте
    private suspend fun oneCallDataFromApi(
        lat: Double,
        lon: Double,
    ): OneCallDataJson {
        return remoteApi.oneCallRequest(
            lat = lat,
            lon = lon,
            exclude = "minutely",
            lang = "ru",
            units = "metric",
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}
