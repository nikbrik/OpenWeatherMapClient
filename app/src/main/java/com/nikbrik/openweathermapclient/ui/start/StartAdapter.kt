package com.nikbrik.openweathermapclient.ui.start

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallData

class StartAdapter(private val itemCallback: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<OneCallData>(OneCallDataDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(StartAdapterDelegate(itemCallback))
    }

    class OneCallDataDiffUtilCallback : DiffUtil.ItemCallback<OneCallData>() {
        override fun areItemsTheSame(
            oldItem: OneCallData,
            newItem: OneCallData
        ): Boolean {
            return oldItem.lat == newItem.lat && oldItem.lon == newItem.lon
        }

        override fun areContentsTheSame(
            oldItem: OneCallData,
            newItem: OneCallData
        ): Boolean {
            return oldItem == newItem
        }
    }
}
