package com.nikbrik.openweathermapclient.ui.detail.hourly

import androidx.recyclerview.widget.RecyclerView
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeatherWithLists
import com.nikbrik.openweathermapclient.databinding.ItemHourlyBinding

class HourlyViewHolder(
    private val binding: ItemHourlyBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(hourlyWeatherWithLists: HourlyWeatherWithLists) {
        binding.textView.text = hourlyWeatherWithLists.toString()
    }
}
