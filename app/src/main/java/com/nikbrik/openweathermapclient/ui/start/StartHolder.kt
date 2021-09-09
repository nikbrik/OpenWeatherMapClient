package com.nikbrik.openweathermapclient.ui.start

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nikbrik.openweathermapclient.R
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataWithLists
import com.nikbrik.openweathermapclient.data.weather_data.weather.Weather
import com.nikbrik.openweathermapclient.databinding.ItemStartListBinding

class StartHolder(
    private val binding: ItemStartListBinding,
    private val itemCallback: (position: Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ocd: OneCallDataWithLists) {

        val current = ocd.current.firstOrNull()
        current?.apply {
            binding.temp.text = String.format("%+.0f°", entity.temp)
            val weather = weatherList.firstOrNull()
            weather?.also {
                binding.icon.load("${Weather.ICON_PATH}${it.icon}${Weather.ICON_FILE_NAME}")
            }
        }

        binding.name.text = if (ocd.entity.id == 0L) {
            binding.root.resources.getString(R.string.current_location)
        } else {
            ocd.entity.name
        }
        binding.coordinates.text =
            binding.root.resources.getString(R.string.coordinates, ocd.entity.lat, ocd.entity.lon)

        binding.root.setOnClickListener {
            itemCallback(bindingAdapterPosition)
        }
    }
}
