package com.nikbrik.openweathermapclient.ui.detail.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikbrik.openweathermapclient.R
import com.nikbrik.openweathermapclient.app.withArguments
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeather
import com.nikbrik.openweathermapclient.databinding.FragmentDailyBinding
import com.nikbrik.openweathermapclient.extensions.autoCleared

class DailyFragment : Fragment(R.layout.fragment_daily) {

    private var binding: FragmentDailyBinding? = null
    private var dailyAdapter: DailyListAdapter by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyBinding.inflate(
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
        dailyAdapter = DailyListAdapter()
        binding?.recyclerView?.apply {
            adapter = dailyAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        dailyAdapter.apply {
            val dailyWeatherArray =
                requireArguments().getParcelableArray(KEY_DAILY_LIST)
            dailyWeatherArray?.apply {
                val dailyWeatherArray = map { it as DailyWeather }
                items = dailyWeatherArray
            }
        }
    }

    companion object {
        private const val KEY_DAILY_LIST = "KEY_DAILY_LIST"

        fun newInstance(
            dailyList: List<DailyWeather>
        ): DailyFragment {
            return DailyFragment().withArguments {
                putParcelableArray(KEY_DAILY_LIST, dailyList.toTypedArray())
            }
        }
    }
}
