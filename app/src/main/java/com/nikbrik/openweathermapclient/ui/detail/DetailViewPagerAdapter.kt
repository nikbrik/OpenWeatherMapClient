package com.nikbrik.openweathermapclient.ui.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeatherWithLists
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeatherWithLists
import com.nikbrik.openweathermapclient.ui.detail.daily.DailyFragment
import com.nikbrik.openweathermapclient.ui.detail.hourly.HourlyFragment

class DetailViewPagerAdapter(
    private val hourlyWeatherList: List<HourlyWeatherWithLists>,
    private val dailyWeatherList: List<DailyWeatherWithLists>,
    fragment: DetailFragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HourlyFragment.newInstance(hourlyWeatherList)
            1 -> DailyFragment.newInstance(dailyWeatherList)
            else -> error("Unknown type of fragment in view pager")
        }
    }
}
