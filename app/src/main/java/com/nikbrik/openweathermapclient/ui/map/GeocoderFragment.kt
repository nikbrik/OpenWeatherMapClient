package com.nikbrik.openweathermapclient.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikbrik.openweathermapclient.R
import com.nikbrik.openweathermapclient.databinding.FragmentGeocoderBinding
import com.nikbrik.openweathermapclient.extensions.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class GeocoderFragment : Fragment(R.layout.fragment_geocoder) {
    private var binding: FragmentGeocoderBinding? = null
    private val viewModel: GeocoderViewModel by viewModels()
    private var geocoderAdapter: GeocoderListAdapter by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGeocoderBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        observe()
        addListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
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
        binding?.apply {

            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            val search = toolbar.menu.findItem(R.id.search)

            val actionSearchView = search.actionView as SearchView
            actionSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { viewModel.searchLocation(it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
    }

    private fun initList() {
        geocoderAdapter = GeocoderListAdapter { position ->
            findNavController().navigate(
                GeocoderFragmentDirections.actionGeocoderFragmentToStartFragment(
                    geocoderAdapter.items.getOrNull(position)
                ),
            )
        }
        binding?.recyclerView?.apply {
            adapter = geocoderAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}
