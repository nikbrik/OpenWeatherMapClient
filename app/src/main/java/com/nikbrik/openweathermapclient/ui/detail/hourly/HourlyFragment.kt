package com.nikbrik.openweathermapclient.ui.detail.hourly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikbrik.openweathermapclient.R
import com.nikbrik.openweathermapclient.app.withArguments
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeather
import com.nikbrik.openweathermapclient.databinding.FragmentHourlyBinding
import com.nikbrik.openweathermapclient.extensions.autoCleared

class HourlyFragment : Fragment(R.layout.fragment_hourly) {

    private var binding: FragmentHourlyBinding? = null
    private var hourlyAdapter: HourlyListAdapter by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHourlyBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
    }

    private fun initList() {
        hourlyAdapter = HourlyListAdapter()
        binding?.recyclerView?.apply {
            adapter = hourlyAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        hourlyAdapter.apply {
            val hourlyWeatherArray = requireArguments().getParcelableArray(KEY_HOURLY_LIST)
            hourlyWeatherArray?.apply {
                val hourlyWeatherList = map { it as HourlyWeather }
                items = hourlyWeatherList
            }
        }
    }

    companion object {
        private const val KEY_HOURLY_LIST = "KEY_HOURLY_LIST"

        fun newInstance(
            hourlyList: List<HourlyWeather>
        ): HourlyFragment {
            return HourlyFragment().withArguments {
                putParcelableArray(KEY_HOURLY_LIST, hourlyList.toTypedArray())
            }
        }
    }
}
