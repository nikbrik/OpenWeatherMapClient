package com.nikbrik.openweathermapclient.ui.map

import androidx.recyclerview.widget.RecyclerView
import com.nikbrik.openweathermapclient.data.geocoder.Location
import com.nikbrik.openweathermapclient.databinding.ItemGeocoderBinding

class GeocoderViewHolder(
    private val binding: ItemGeocoderBinding,
    itemCallback: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            itemCallback(bindingAdapterPosition)
        }
    }

    fun bind(location: Location) {
        binding.textView.text = location.toString()
    }
}
