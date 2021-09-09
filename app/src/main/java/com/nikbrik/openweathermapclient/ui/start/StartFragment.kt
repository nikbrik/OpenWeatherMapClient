package com.nikbrik.openweathermapclient.ui.start

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.nikbrik.openweathermapclient.R
import com.nikbrik.openweathermapclient.app.toast
import com.nikbrik.openweathermapclient.databinding.FragmentStartScreenBinding
import com.nikbrik.openweathermapclient.extensions.autoCleared
import timber.log.Timber
import java.util.concurrent.TimeUnit

class StartFragment : Fragment(R.layout.fragment_start_screen) {
    private var binding: FragmentStartScreenBinding? = null
    private val viewModel: StartViewModel by viewModels()
    private var ocdAdapter: StartAdapter by autoCleared()
    private val args: StartFragmentArgs by navArgs()

    // Получение текущего местоположения
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
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
                        if (isGranted) getLastLocation()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (this::fusedLocationClient.isInitialized.not()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        }

        if (this::locationCallback.isInitialized.not()) {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult ?: return
                    locationResult.lastLocation.apply {
                        refreshData(latitude, longitude)
                    }
                }
            }
        }

        if (this::locationRequest.isInitialized.not()) {
            locationRequest = LocationRequest.create().apply {
                interval = TimeUnit.MINUTES.toMillis(10)
                fastestInterval = TimeUnit.MINUTES.toMillis(5)
                maxWaitTime = TimeUnit.MINUTES.toMillis(20)
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartScreenBinding.inflate(
            LayoutInflater.from(requireContext()), container, false
        )
        return binding!!.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkGooglePlayServices()

        if (isLocationPermissionGranted()) {
            getLastLocation()
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

    override fun onResume() {
        super.onResume()
        doIfGoogleApiAvailable {
            if (isLocationPermissionGranted()) {
                startLocationUpdates()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // +++ Получение местоположения и связанных разрешений

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                it ?: return@addOnSuccessListener
                refreshData(it.latitude, it.longitude)
            }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocationImmediately() {
        fusedLocationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            object : CancellationToken() {
                override fun isCancellationRequested(): Boolean {
                    return false
                }

                override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                    return this
                }
            }
        )
            .addOnSuccessListener {
                refreshData(it.latitude, it.longitude)
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
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }
    // --- Получение местоположения и связанных разрешений

    private fun refreshData(lat: Double, lon: Double) {
        viewModel.setCurrentLocation(lat, lon)
        viewModel.getStartData()
    }

    @SuppressLint("MissingPermission")
    private fun addListeners() {
        binding?.addElement?.setOnClickListener {
            findNavController().navigate(
                StartFragmentDirections.actionStartFragmentToGeocoderFragment()
            )
        }
        binding?.apply {
            toolbar.menu.findItem(R.id.refresh).setOnMenuItemClickListener {
                doIfGoogleApiAvailable {
                    if (isLocationPermissionGranted()) {
                        getCurrentLocationImmediately()
                    } else {
                        tryGetLocationPermission()
                    }
                }
                true
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
        binding?.recyclerView?.apply {
            adapter = ocdAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}
