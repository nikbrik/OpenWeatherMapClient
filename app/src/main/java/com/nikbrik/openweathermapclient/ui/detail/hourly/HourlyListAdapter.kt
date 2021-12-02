package com.nikbrik.openweathermapclient.ui.detail.hourly

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeather

class HourlyListAdapter() :
    AsyncListDifferDelegationAdapter<HourlyWeather>(HourlyDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(HourlyAdapterDelegate())
    }

    class HourlyDiffUtilCallback : DiffUtil.ItemCallback<HourlyWeather>() {
        override fun areItemsTheSame(
            oldItem: HourlyWeather,
            newItem: HourlyWeather
        ): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(
            oldItem: HourlyWeather,
            newItem: HourlyWeather
        ): Boolean {
            return oldItem == newItem
        }
    }
}
