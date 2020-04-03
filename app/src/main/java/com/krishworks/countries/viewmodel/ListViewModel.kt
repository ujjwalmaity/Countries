package com.krishworks.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krishworks.countries.model.*

class ListViewModel : ViewModel() {

    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        val mockData = listOf(
            Country("A"),
            Country("B"),
            Country("C"),
            Country("D"),
            Country("E"),
            Country("F"),
            Country("G"),
            Country("H"),
            Country("I"),
            Country("J")
        )

        countries.value = mockData
        countryLoadError.value = false
        loading.value = false
    }
}