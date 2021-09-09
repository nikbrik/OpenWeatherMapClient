package com.nikbrik.openweathermapclient.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nikbrik.openweathermapclient.R

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restoreLangs()
    }

    private fun restoreLangs() {
        // Получение списка языков
        if (viewModel.langs.isEmpty()) {
            viewModel.langs = resources.getStringArray(R.array.langs)
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.item_settings, viewModel.langs)
//        binding.langAct.setAdapter(adapter)
    }
}
