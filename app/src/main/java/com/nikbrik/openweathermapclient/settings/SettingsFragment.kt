package com.nikbrik.openweathermapclient.settings

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nikbrik.openweathermapclient.R
import com.nikbrik.openweathermapclient.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val binding: FragmentSettingsBinding by viewBinding()
    private val viewModel = SettingsViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restoreLangs()
    }

    private fun restoreLangs() {
        // Получение списка языков
        if (viewModel.langs.isEmpty()) {
            viewModel.langs = resources.getStringArray(R.array.langs)
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, viewModel.langs)
        binding.langAct.setAdapter(adapter)
    }
}
