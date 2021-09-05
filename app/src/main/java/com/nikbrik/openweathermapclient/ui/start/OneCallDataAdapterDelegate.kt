package com.nikbrik.openweathermapclient.ui.start

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataWithLists
import com.nikbrik.openweathermapclient.databinding.StartListItemBinding

class OneCallDataAdapterDelegate() :
    AbsListItemAdapterDelegate<
        OneCallDataWithLists,
        OneCallDataWithLists,
        OneCallDataHolder>() {

    override fun isForViewType(
        item: OneCallDataWithLists,
        items: MutableList<OneCallDataWithLists>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): OneCallDataHolder {
        return OneCallDataHolder(
            StartListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        item: OneCallDataWithLists,
        holder: OneCallDataHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item.toString())
    }
}
