package com.nikbrik.openweathermapclient.ui.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nikbrik.openweathermapclient.data.weather_data.daily_weather.DailyWeather
import com.nikbrik.openweathermapclient.data.weather_data.hourly_weather.HourlyWeather
import com.nikbrik.openweathermapclient.ui.detail.daily.DailyFragment
import com.nikbrik.openweathermapclient.ui.detail.hourly.HourlyFragment

class DetailViewPagerAdapter(
    private val hourlyWeatherList: List<HourlyWeather>,
    private val dailyWeatherList: List<DailyWeather>,
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
