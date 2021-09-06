package com.nikbrik.openweathermapclient.ui.detail.daily

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeatherWithLists

class DailyListAdapter() :
    AsyncListDifferDelegationAdapter<DailyWeatherWithLists>(DailyDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(DailyAdapterDelegate())
    }

    class DailyDiffUtilCallback : DiffUtil.ItemCallback<DailyWeatherWithLists>() {
        override fun areItemsTheSame(
            oldItem: DailyWeatherWithLists,
            newItem: DailyWeatherWithLists
        ): Boolean {
            return oldItem.entity.dt == newItem.entity.dt &&
                oldItem.entity.parent_id == newItem.entity.parent_id
        }

        override fun areContentsTheSame(
            oldItem: DailyWeatherWithLists,
            newItem: DailyWeatherWithLists
        ): Boolean {
            return oldItem == newItem
        }
    }
}
