package com.nikbrik.openweathermapclient.ui.detail.daily

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nikbrik.openweathermapclient.R
import com.nikbrik.openweathermapclient.app.withArguments
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeatherWithLists
import com.nikbrik.openweathermapclient.databinding.FragmentDailyBinding

class DailyFragment : Fragment(R.layout.fragment_daily) {

    private val binding: FragmentDailyBinding by viewBinding()

    companion object {
        private const val KEY_DAILY_LIST = "KEY_DAILY_LIST"

        fun newInstance(
            dailyList: List<DailyWeatherWithLists>
        ): DailyFragment {
            return DailyFragment().withArguments {
                putParcelableArray(KEY_DAILY_LIST, dailyList.toTypedArray())
            }
        }
    }
}
