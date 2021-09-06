package com.nikbrik.openweathermapclient.ui.detail.hourly

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nikbrik.openweathermapclient.R
import com.nikbrik.openweathermapclient.app.withArguments
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeatherWithLists
import com.nikbrik.openweathermapclient.databinding.FragmentHourlyBinding
import com.nikbrik.openweathermapclient.extensions.autoCleared

class HourlyFragment : Fragment(R.layout.fragment_hourly) {

    private val binding: FragmentHourlyBinding by viewBinding()
    private var hourlyAdapter: HourlyListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
    }

    private fun initList() {
        hourlyAdapter = HourlyListAdapter()
        binding.recyclerView.apply {
            adapter = hourlyAdapter
            layoutManager = LinearLayoutManager(requireContext())
//            setHasFixedSize(true)
        }
        hourlyAdapter.apply {
            val hourlyWeatherArray = requireArguments().getParcelableArray(KEY_HOURLY_LIST)
            hourlyWeatherArray?.apply {
                val hourlyWeatherList = map { it as HourlyWeatherWithLists }
                items = hourlyWeatherList
            }
        }
    }

    companion object {
        private const val KEY_HOURLY_LIST = "KEY_HOURLY_LIST"

        fun newInstance(
            hourlyList: List<HourlyWeatherWithLists>
        ): HourlyFragment {
            return HourlyFragment().withArguments {
                putParcelableArray(KEY_HOURLY_LIST, hourlyList.toTypedArray())
            }
        }
    }
}
