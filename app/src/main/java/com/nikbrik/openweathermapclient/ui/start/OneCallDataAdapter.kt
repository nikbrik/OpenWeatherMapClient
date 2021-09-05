package com.nikbrik.openweathermapclient.ui.start

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataWithLists

class OneCallDataAdapter :
    AsyncListDifferDelegationAdapter<OneCallDataWithLists>(OneCallDataDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(OneCallDataAdapterDelegate())
    }

    class OneCallDataDiffUtilCallback : DiffUtil.ItemCallback<OneCallDataWithLists>() {
        override fun areItemsTheSame(
            oldItem: OneCallDataWithLists,
            newItem: OneCallDataWithLists
        ): Boolean {
            return oldItem.entity.id == newItem.entity.id
        }

        override fun areContentsTheSame(
            oldItem: OneCallDataWithLists,
            newItem: OneCallDataWithLists
        ): Boolean {
            return oldItem == newItem
        }
    }
}
