package com.example.weatherapp.fragments.home

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
//import com.example.weatherapp.Manifest
import com.example.weatherapp.data.CurrentLocation
import com.example.weatherapp.databinding.FragmentHomeBinding
//import java.sql.Date
import java.util.Locale

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val weatherDataAdapter = WeatherDataAdapter(
        onLocationClicked = {showLocationOptions() }

    )

    private val locationPermissionLaucher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
            isGranted ->
        if(isGranted) {
            getCurrentLocation()
        } else {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWeatherDataAdapter()
        setWeatherData()
    }

    private fun setWeatherDataAdapter(){
        binding.weatherDataRecyclerView.adapter = weatherDataAdapter
    }

    private fun setWeatherData(){
        weatherDataAdapter.setData(data = listOf(CurrentLocation()))
    }


    private fun getCurrentLocation() {
        Toast.makeText(requireContext(), "getCurrentLocation()", Toast.LENGTH_SHORT).show()
    }

    private fun isLocationPermissionGranted() : Boolean{
        return ContextCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission(){
        locationPermissionLaucher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }
    private fun proceedWithCurrentLocation() {
        if(isLocationPermissionGranted()){
            getCurrentLocation()
        } else {
            requestLocationPermission()
        }
    }
    private fun showLocationOptions(){
        val options = arrayOf("Current Location", "Search Manually")
        AlertDialog.Builder(requireContext()).apply {
            setTitle("choose location method")
            setItems(options) { _, which ->
                when(which) {
                    0 -> proceedWithCurrentLocation()
                }

            }
            show()
        }
    }

}