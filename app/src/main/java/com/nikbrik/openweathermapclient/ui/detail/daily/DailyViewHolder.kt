package com.nikbrik.openweathermapclient.ui.detail.daily

import androidx.recyclerview.widget.RecyclerView
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeatherWithLists
import com.nikbrik.openweathermapclient.databinding.ItemDailyBinding

class DailyViewHolder(
    private val binding: ItemDailyBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(dailyWeatherWithLists: DailyWeatherWithLists) {
    }
}
