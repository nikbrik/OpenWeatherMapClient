package com.nikbrik.openweathermapclient.ui.detail.hourly

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeather
import com.nikbrik.openweathermapclient.databinding.ItemHourlyBinding

class HourlyAdapterDelegate() :
    AbsListItemAdapterDelegate<HourlyWeather, HourlyWeather, HourlyViewHolder>() {

    override fun isForViewType(
        item: HourlyWeather,
        items: MutableList<HourlyWeather>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): HourlyViewHolder {
        return HourlyViewHolder(
            ItemHourlyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(
        item: HourlyWeather,
        holder: HourlyViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}
