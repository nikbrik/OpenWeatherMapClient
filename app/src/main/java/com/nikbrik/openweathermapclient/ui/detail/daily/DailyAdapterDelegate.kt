package com.nikbrik.openweathermapclient.ui.detail.daily

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeather
import com.nikbrik.openweathermapclient.databinding.ItemDailyBinding

class DailyAdapterDelegate() :
    AbsListItemAdapterDelegate<DailyWeather, DailyWeather, DailyViewHolder>() {

    override fun isForViewType(
        item: DailyWeather,
        items: MutableList<DailyWeather>,
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
        item: DailyWeather,
        holder: DailyViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}
