package com.example.weatherapp.fragments.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.RemoteLocation
import com.example.weatherapp.network.repository.WeatherDataRepository
import kotlinx.coroutines.launch


class LocationViewModel(private val weatherDataRepository: WeatherDataRepository) : ViewModel() {

    private val _searchResult = MutableLiveData<SearchResultDataState>()
    val searchResult: LiveData<SearchResultDataState> get() = _searchResult

    fun searchLocation(query: String){
    viewModelScope.launch {
        emitSearchResultUiState(isLoading = true)
        val searchResult = weatherDataRepository.searchLocation(query)
        if (searchResult.isNullOrEmpty()){
            emitSearchResultUiState(error="Location is not found, please try again!")
        } else {
            emitSearchResultUiState(loacations = searchResult)
        }
     }
    }

    private fun emitSearchResultUiState(
        isLoading: Boolean = false,
        loacations: List<RemoteLocation>?=null,
        error: String?=null
    ) {
        val searchResultDataState = SearchResultDataState(isLoading, loacations, error)
        _searchResult.value=searchResultDataState
    }


    data class SearchResultDataState(
        val isLoading: Boolean,
        val loacations: List<RemoteLocation>?,
        val error: String?
    )
}