package com.nikbrik.openweathermapclient.ui.map

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.nikbrik.openweathermapclient.data.geocoder.Location

class GeocoderListAdapter(private val itemCallback: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<Location>(GeocoderDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(GeocoderAdapterDelegate(itemCallback))
    }

    class GeocoderDiffUtilCallback : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.value == newItem.value
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }
    }
}
