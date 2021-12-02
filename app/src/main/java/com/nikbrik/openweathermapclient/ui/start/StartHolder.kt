package com.nikbrik.openweathermapclient.ui.start

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nikbrik.openweathermapclient.R
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallData
import com.nikbrik.openweathermapclient.data.weather_data.ocd.OneCallDataEntity.Companion.CURRENT_NAME
import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherJson.Companion.ICON_FILE_NAME
import com.nikbrik.openweathermapclient.data.weather_data.weather.WeatherJson.Companion.ICON_PATH
import com.nikbrik.openweathermapclient.databinding.ItemStartListBinding

class StartHolder(
    private val binding: ItemStartListBinding,
    private val itemCallback: (position: Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ocd: OneCallData) {

        ocd.current.apply {
            binding.temp.text = String.format("%+.0f°", temp)
            binding.icon.load("${ICON_PATH}${weather.icon}$ICON_FILE_NAME")
        }

        binding.name.text = if (ocd.name == CURRENT_NAME) {
            binding.root.resources.getString(R.string.current_location)
        } else {
            ocd.name
        }
        binding.coordinates.text =
            binding.root.resources.getString(R.string.coordinates, ocd.lat, ocd.lon)

        binding.root.setOnClickListener {
            itemCallback(bindingAdapterPosition)
        }
    }
}
