package com.nikbrik.openweathermapclient.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nikbrik.openweathermapclient.R
import com.nikbrik.openweathermapclient.app.toast
import com.nikbrik.openweathermapclient.databinding.FragmentPermissionRequestBinding

class PermissionRequestFragment : Fragment(R.layout.fragment_permission_request) {

    private val binding: FragmentPermissionRequestBinding by viewBinding()

    // Контракт для запроса разрешений
    private val requestMultiplePermissions =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            Manifest.permission.ACCESS_FINE_LOCATION
            permissions.forEach {
                val isGranted = it.value
                when (it.key) {
                    Manifest.permission.ACCESS_FINE_LOCATION -> {
                        processRequestResult(isGranted)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermissions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.permissionRequestButton.setOnClickListener {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.need_permissions))
                    .setPositiveButton("OK") { _, _ ->
                        requestLocationPermission()
                    }
                    .setNegativeButton("Cancel") { _, _ ->
                        needLocationPermissionToast()
                    }
                    .show()
            else
                requestLocationPermission()
        }
    }

    private fun checkPermissions() {
        val isLocationPermissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        processRequestResult(isLocationPermissionGranted)
    }

    private fun processRequestResult(isLocationPermissionGranted: Boolean) {
        if (isLocationPermissionGranted) {
//            findNavController().navigate(
//                PermissionRequestFragmentDirections.actionPermissionRequestFragmentToStartFragment(
//                )
//            )
        } else {
            needLocationPermissionToast()
        }
    }

    private fun needLocationPermissionToast() = toast(getString(R.string.need_permissions))

    private fun requestLocationPermission() {
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )
    }
}
