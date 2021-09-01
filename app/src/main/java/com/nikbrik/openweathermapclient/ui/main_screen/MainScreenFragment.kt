package com.nikbrik.openweathermapclient.ui.main_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nikbrik.openweathermapclient.R
import com.nikbrik.openweathermapclient.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment(R.layout.fragment_main_screen) {
    private val binding: FragmentMainScreenBinding by viewBinding()
    private val viewModel = MainScreenViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            findNavController().navigate(
                MainScreenFragmentDirections.actionMainScreenFragmentToSettingsFragment()
            )
        }

        observeLiveData()
        viewModel.getData()
    }

    private fun observeLiveData() {
        observeOneCallData()
        observeError()
    }

    private fun observeError() {
        viewModel.error.observe(
            viewLifecycleOwner,
            {
                setTextView(it)
            }
        )
    }

    private fun observeOneCallData() {
        viewModel.oneCallData.observe(
            viewLifecycleOwner,
            {
                setTextView(it.toString())
            }
        )
    }

    private fun setTextView(string: String) {
        binding.textView.text = string
    }
}
