package com.nikbrik.openweathermapclient.ui.start

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.nikbrik.openweathermapclient.R
import com.nikbrik.openweathermapclient.app.toast
import com.nikbrik.openweathermapclient.databinding.FragmentStartScreenBinding
import com.nikbrik.openweathermapclient.extensions.autoCleared
import timber.log.Timber
import java.util.concurrent.TimeUnit

class StartFragment : Fragment(R.layout.fragment_start_screen) {
    private val binding: FragmentStartScreenBinding by viewBinding()
    private val viewModel = StartViewModel()
    private var ocdAdapter: StartAdapter by autoCleared()
    private val args: StartFragmentArgs by navArgs()

    // +++ РАБОТА С ГЕОЛОКАЦИЕЙ

    // Получение текущего местоположения
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private var requestingLocationUpdates = true
    private lateinit var locationCallback: LocationCallback

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

    @SuppressLint("MissingPermission")
    private fun processRequestResult(isLocationPermissionGranted: Boolean) {
        if (isLocationPermissionGranted) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener {
                    refreshData(it.latitude, it.longitude)
                }
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )
    }

    private fun tryGetLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.need_permissions))
                .setPositiveButton(getString(R.string.rationale_dialog_ok)) { _, _ ->
                    requestLocationPermission()
                }
                .setNegativeButton(getString(R.string.rationale_dialog_cancel)) { _, _ ->
                    needLocationPermissionToast()
                }
                .show()
        else
            requestLocationPermission()
    }

    private fun needLocationPermissionToast() = toast(getString(R.string.need_permissions))

    private fun doIfGoogleApiAvailable(action: () -> Unit) {
        GoogleApiAvailability.getInstance()
            .checkApiAvailability(fusedLocationClient)
            .addOnSuccessListener { run(action) }
    }

    private fun refreshData(lat: Double, lon: Double) {
        viewModel.setCurrentLocation(lat, lon)
        viewModel.getStartData()
    }

    private fun checkGooglePlayServices() {
        val result = GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(requireContext())
        GoogleApiAvailability.getInstance()
            .getErrorDialog(this, result, 1)
            ?.apply {
                when (result) {
                    ConnectionResult.SUCCESS -> {
                        setCancelable(true)
                    }
                    ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED -> {
                        setCancelable(true)
                    }
                    else -> {
                        setCancelable(false)
                    }
                }
            }
            ?.show()
    }

    private fun stopLocationUpdates() {
        requestingLocationUpdates = true
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        requestingLocationUpdates = false

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                locationResult.lastLocation.apply {
                    refreshData(latitude, longitude)
                }
            }
        }

        locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(10)
            fastestInterval = TimeUnit.SECONDS.toMillis(5)
            maxWaitTime = TimeUnit.MINUTES.toMillis(5)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
    // --- РАБОТА С ГЕОЛОКАЦИЕЙ

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkGooglePlayServices()
        if (isLocationPermissionGranted()) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener {
                    it ?: return@addOnSuccessListener
                    refreshData(it.latitude, it.longitude)
                }
        } else {
            tryGetLocationPermission()
        }

        addListeners()
        initList()
        observe()

        args.location?.let {
            viewModel.addNew(it.value, it.getCoordinates())
        }
    }

    override fun onStart() {
        super.onStart()
        doIfGoogleApiAvailable {
            if (isLocationPermissionGranted()) {
                startLocationUpdates()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        doIfGoogleApiAvailable {
            if (isLocationPermissionGranted()) {
                stopLocationUpdates()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun addListeners() {
        binding.addElement.setOnClickListener {
            findNavController().navigate(
                StartFragmentDirections.actionStartFragmentToGeocoderFragment()
            )
        }
        binding.refresh.setOnClickListener {
            doIfGoogleApiAvailable {
                if (isLocationPermissionGranted()) {
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener {
                            refreshData(it.latitude, it.longitude)
                        }
                } else {
                    tryGetLocationPermission()
                }
            }
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
        ocdAdapter = StartAdapter { position ->
            findNavController().navigate(
                StartFragmentDirections.actionStartFragmentToDetailFragment(
                    ocdAdapter.items[position]
                )
            )
        }
        binding.recyclerView.apply {
            adapter = ocdAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}
