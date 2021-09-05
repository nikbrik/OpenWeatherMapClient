package com.nikbrik.openweathermapclient.ui.map

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.nikbrik.openweathermapclient.data.geocoder.Location
import com.nikbrik.openweathermapclient.databinding.GeocoderItemBinding

class GeocoderAdapterDelegate(private val itemCallback: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<Location, Location, GeocoderViewHolder>() {
    override fun isForViewType(
        item: Location,
        items: MutableList<Location>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): GeocoderViewHolder {
        return GeocoderViewHolder(
            GeocoderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemCallback
        )
    }

    override fun onBindViewHolder(
        item: Location,
        holder: GeocoderViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}
