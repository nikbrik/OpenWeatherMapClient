package com.nikbrik.openweathermapclient.ui.detail.hourly

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeatherWithLists
import com.nikbrik.openweathermapclient.data.weather_data.weather.Weather.Companion.ICON_FILE_NAME
import com.nikbrik.openweathermapclient.data.weather_data.weather.Weather.Companion.ICON_PATH
import com.nikbrik.openweathermapclient.databinding.ItemHourlyBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class HourlyViewHolder(
    private val binding: ItemHourlyBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(hourly: HourlyWeatherWithLists) {
        val weatherEntity = hourly.weatherList.firstOrNull()
        weatherEntity?.let {
            binding.icon.load("$ICON_PATH${it.icon}$ICON_FILE_NAME")
            binding.description.text = it.description
        }
        binding.temp.text = String.format("%+.0f°", hourly.entity.temp)

        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        binding.time.text = Instant.ofEpochSecond(hourly.entity.dt)
            .atZone(ZoneId.systemDefault())
            .format(formatter)
    }
}
