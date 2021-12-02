package com.nikbrik.openweathermapclient.ui.start

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallData
import com.nikbrik.openweathermapclient.databinding.ItemStartListBinding

class StartAdapterDelegate(private val itemCallback: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<
        OneCallData,
        OneCallData,
        StartHolder>() {

    override fun isForViewType(
        item: OneCallData,
        items: MutableList<OneCallData>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): StartHolder {
        return StartHolder(
            ItemStartListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemCallback,
        )
    }

    override fun onBindViewHolder(
        item: OneCallData,
        holder: StartHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}
