package com.nikbrik.openweathermapclient.ui.detail.daily

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeatherWithLists
import com.nikbrik.openweathermapclient.databinding.ItemDailyBinding

class DailyAdapterDelegate() :
    AbsListItemAdapterDelegate<DailyWeatherWithLists, DailyWeatherWithLists, DailyViewHolder>() {

    override fun isForViewType(
        item: DailyWeatherWithLists,
        items: MutableList<DailyWeatherWithLists>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): DailyViewHolder {
        return DailyViewHolder(
            ItemDailyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(
        item: DailyWeatherWithLists,
        holder: DailyViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}
