package com.nikbrik.openweathermapclient

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikbrik.openweathermapclient.data.weather_data.daily_temp.DailyTempContract

@Entity(
    tableName = DailyTempContract.TABLE_NAME
)
data class DailyTempEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DailyTempContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = DailyTempContract.Columns.MORNING)
    val morn: Float,
    @ColumnInfo(name = DailyTempContract.Columns.DAY)
    val day: Float,
    @ColumnInfo(name = DailyTempContract.Columns.EVENING)
    val eve: Float,
    @ColumnInfo(name = DailyTempContract.Columns.NIGHT)
    val night: Float,
    @ColumnInfo(name = DailyTempContract.Columns.MINIMUM)
    val min: Float,
    @ColumnInfo(name = DailyTempContract.Columns.MAXIMUM)
    val max: Float,
) {
    companion object {
        fun fromJson(json: DailyTempJson): DailyTempEntity {
            return json.run {
                DailyTempEntity(
                    id = 0,
                    morn = morn,
                    day = day,
                    eve = eve,
                    night = night,
                    min = min ?: 0F,
                    max = max ?: 0F,
                )
            }
        }
    }
}
