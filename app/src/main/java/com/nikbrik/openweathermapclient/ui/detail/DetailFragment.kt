package com.nikbrik.openweathermapclient.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.nikbrik.openweathermapclient.R
import com.nikbrik.openweathermapclient.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val binding: FragmentDetailBinding by viewBinding()
    private val viewModel = DetailViewModel()
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeLiveData()
    }

    private fun initViews() {
        // Инициализация вьюпейджера
        binding.viewPager.adapter = DetailViewPagerAdapter(
            args.oneCallDataWithLists.hourly,
            args.oneCallDataWithLists.daily,
            fragment = this
        )

        // Установка вкладок
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Сутки"
                1 -> tab.text = "Неделя"
            }
        }.attach()
    }

    private fun observeLiveData() {
        observeOneCallData()
        observeError()
    }

    private fun observeError() {
        viewModel.error.observe(
            viewLifecycleOwner,
            {
            }
        )
    }

    private fun observeOneCallData() {
        viewModel.oneCallData.observe(
            viewLifecycleOwner,
            {
            }
        )
    }
}
