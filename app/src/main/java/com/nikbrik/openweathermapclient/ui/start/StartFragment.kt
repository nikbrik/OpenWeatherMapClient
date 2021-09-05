package com.nikbrik.openweathermapclient.ui.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nikbrik.openweathermapclient.R
import com.nikbrik.openweathermapclient.databinding.FragmentStartScreenBinding
import com.nikbrik.openweathermapclient.extensions.autoCleared
import timber.log.Timber

class StartFragment : Fragment(R.layout.fragment_start_screen) {
    private val binding: FragmentStartScreenBinding by viewBinding()
    private val viewModel = StartViewModel()
    private var ocdAdapter: OneCallDataAdapter by autoCleared()
    private val args: StartFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()
        initList()
        observe()
        args.location?.let {
            viewModel.addNew(it.getCoordinates())
        } ?: run {
            viewModel.getStartData()
        }
    }

    private fun addListeners() {
        binding.addElement.setOnClickListener {
            findNavController().navigate(
                StartFragmentDirections.actionStartFragmentToGeocoderFragment()
            )
        }
    }

    private fun observe() {
        viewModel.ocdList.observe(
            viewLifecycleOwner,
            { ocdAdapter.items = it }
        )
        viewModel.error.observe(
            viewLifecycleOwner,
            { t: Throwable -> Timber.e(t) }
        )
    }

    private fun initList() {
        ocdAdapter = OneCallDataAdapter()
        binding.recyclerView.apply {
            adapter = ocdAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}
