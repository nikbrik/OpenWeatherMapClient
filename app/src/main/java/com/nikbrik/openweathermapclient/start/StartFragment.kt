package com.nikbrik.openweathermapclient.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class StartFragment : Fragment() {
    private val viewModel = StartViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findNavController().navigate(
            StartFragmentDirections.actionStartFragmentToMainScreenFragment()
        )
    }
}
