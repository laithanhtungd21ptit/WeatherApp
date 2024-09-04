package com.example.weatherapp.data

sealed class WeatherData
data class CurrentLocation(
    val Date: String,
    val Location: String = "Choose your location"

): WeatherData()