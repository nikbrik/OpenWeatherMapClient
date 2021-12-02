package com.nikbrik.openweathermapclient.ui.detail.daily

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeather

class DailyListAdapter() :
    AsyncListDifferDelegationAdapter<DailyWeather>(DailyDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(DailyAdapterDelegate())
    }

    class DailyDiffUtilCallback : DiffUtil.ItemCallback<DailyWeather>() {
        override fun areItemsTheSame(
            oldItem: DailyWeather,
            newItem: DailyWeather
        ): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(
            oldItem: DailyWeather,
            newItem: DailyWeather
        ): Boolean {
            return oldItem == newItem
        }
    }
}
