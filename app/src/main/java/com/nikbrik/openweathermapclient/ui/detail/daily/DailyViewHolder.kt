package com.nikbrik.openweathermapclient.ui.detail.daily

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeatherWithLists
import com.nikbrik.openweathermapclient.data.weather_data.weather.Weather
import com.nikbrik.openweathermapclient.databinding.ItemDailyBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DailyViewHolder(
    private val binding: ItemDailyBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(daily: DailyWeatherWithLists) {
        val weatherEntity = daily.weatherList.firstOrNull()
        weatherEntity?.let {
            binding.icon.load("${Weather.ICON_PATH}${it.icon}${Weather.ICON_FILE_NAME}")
            binding.description.text = it.description
        }
        val temp = daily.temperatureList.firstOrNull { it.min != null || it.max != null }
        temp?.let {
            binding.temp.text = String.format("%+.0f°..%+.0f°", it.min, it.max)
        }

        val formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        binding.date.text = Instant.ofEpochSecond(daily.entity.dt)
            .atZone(ZoneId.systemDefault())
            .format(formatterDate)

        val formatterDay = DateTimeFormatter.ofPattern("EEEE")
        binding.weekDay.text = Instant.ofEpochSecond(daily.entity.dt)
            .atZone(ZoneId.systemDefault())
            .format(formatterDay)
    }
}
