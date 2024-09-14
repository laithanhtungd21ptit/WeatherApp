package com.example.weatherapp.network.api

import com.example.weatherapp.data.RemoteLocation

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    companion object{
        const val BASE_URL="https://api.weatherapi.com/v1/"
        const val API_KEY="e0384f7f780d49cd870173249240909"

    }
    @GET("search.json")
    suspend fun searchLocation(
        @Query("key") key: String = API_KEY,
        @Query("q") query: String
    ): List<RemoteLocation>
}