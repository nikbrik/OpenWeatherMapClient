package com.nikbrik.openweathermapclient.data.weather_data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikbrik.openweathermapclient.DailyTempEntity
import com.nikbrik.openweathermapclient.data.weather_data.OpenWeatherDatabase.Companion.DB_VERSION
import com.nikbrik.openweathermapclient.data.weather_data.daily_temp.DailyTempDao
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeatherDao
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeatherEntity
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeatherDao
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeatherEntity
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataDao
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataEntity
import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherDao
import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(
    entities = [
        OneCallDataEntity::class,
        HourlyWeatherEntity::class,
        DailyWeatherEntity::class,
        DailyTempEntity::class,
        WeatherEntity::class,
    ],
    version = DB_VERSION,
)
abstract class OpenWeatherDatabase : RoomDatabase() {

    abstract fun ocdDao(): OneCallDataDao
    abstract fun hourlyWeatherDao(): HourlyWeatherDao
    abstract fun dailyWeatherDao(): DailyWeatherDao
    abstract fun dailyTempDao(): DailyTempDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        const val DATABASE_NAME = "openweatherdb"
        const val DB_VERSION = 1
    }
}

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): OpenWeatherDatabase {
        return Room.databaseBuilder(
            context,
            OpenWeatherDatabase::class.java,
            OpenWeatherDatabase.DATABASE_NAME,
        )
            .fallbackToDestructiveMigration() // TODO Удалить
            .build()
    }

    @Provides
    fun provideOneCallDataDao(db: OpenWeatherDatabase): OneCallDataDao {
        return db.ocdDao()
    }

    @Provides
    fun provideHourlyWeatherDao(db: OpenWeatherDatabase): HourlyWeatherDao {
        return db.hourlyWeatherDao()
    }

    @Provides
    fun provideDailyWeatherDao(db: OpenWeatherDatabase): DailyWeatherDao {
        return db.dailyWeatherDao()
    }

    @Provides
    fun provideDailyTempDao(db: OpenWeatherDatabase): DailyTempDao {
        return db.dailyTempDao()
    }

    @Provides
    fun provideWeatherDao(db: OpenWeatherDatabase): WeatherDao {
        return db.weatherDao()
    }
}
