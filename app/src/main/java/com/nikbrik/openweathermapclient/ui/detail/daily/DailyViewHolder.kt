package com.nikbrik.openweathermapclient.ui.detail.daily

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeather
import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherJson.Companion.ICON_FILE_NAME
import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherJson.Companion.ICON_PATH
import com.nikbrik.openweathermapclient.databinding.ItemDailyBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DailyViewHolder(
    private val binding: ItemDailyBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(daily: DailyWeather) {
        daily.weather.let {
            binding.icon.load("${ICON_PATH}${it.icon}$ICON_FILE_NAME")
            binding.description.text = it.description
        }
        binding.temp.text = String.format("%+.0f°..%+.0f°", daily.temp.min, daily.temp.max)

        val formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        binding.date.text = Instant.ofEpochSecond(daily.dt)
            .atZone(ZoneId.systemDefault())
            .format(formatterDate)

        val formatterDay = DateTimeFormatter.ofPattern("EEEE")
        binding.weekDay.text = Instant.ofEpochSecond(daily.dt)
            .atZone(ZoneId.systemDefault())
            .format(formatterDay)
    }
}
