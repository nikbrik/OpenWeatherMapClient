package com.nikbrik.openweathermapclient.ui.detail.hourly

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeatherWithLists

class HourlyListAdapter() :
    AsyncListDifferDelegationAdapter<HourlyWeatherWithLists>(HourlyDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(HourlyAdapterDelegate())
    }

    class HourlyDiffUtilCallback : DiffUtil.ItemCallback<HourlyWeatherWithLists>() {
        override fun areItemsTheSame(
            oldItem: HourlyWeatherWithLists,
            newItem: HourlyWeatherWithLists
        ): Boolean {
            return oldItem.entity.dt == newItem.entity.dt
        }

        override fun areContentsTheSame(
            oldItem: HourlyWeatherWithLists,
            newItem: HourlyWeatherWithLists
        ): Boolean {
            return oldItem == newItem
        }
    }
}
