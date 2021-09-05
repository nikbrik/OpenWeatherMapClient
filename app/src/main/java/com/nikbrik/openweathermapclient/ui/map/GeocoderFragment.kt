package com.nikbrik.openweathermapclient.ui.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nikbrik.openweathermapclient.R
import com.nikbrik.openweathermapclient.databinding.FragmentGeocoderBinding
import com.nikbrik.openweathermapclient.extensions.autoCleared
import timber.log.Timber

class GeocoderFragment : Fragment(R.layout.fragment_geocoder) {
    private val binding: FragmentGeocoderBinding by viewBinding()
    private val viewModel = GeocoderViewModel()
    private var geocoderAdapter: GeocoderListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        observe()
        addListeners()
    }

    private fun observe() {
        viewModel.locations.observe(
            viewLifecycleOwner,
            { geocoderAdapter.items = it }
        )
        viewModel.error.observe(
            viewLifecycleOwner,
            { Timber.e(it) }
        )
    }

    private fun addListeners() {
        binding.search.setOnClickListener {
            viewModel.searchLocation(binding.editText.text.toString())
        }
    }

    private fun initList() {
        geocoderAdapter = GeocoderListAdapter { position ->
            findNavController().navigate(
                GeocoderFragmentDirections.actionGeocoderFragmentToStartFragment(
                    geocoderAdapter.items.getOrNull(position)
                )
            )
        }
        binding.recyclerView.apply {
            adapter = geocoderAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}
