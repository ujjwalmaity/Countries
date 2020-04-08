package com.krishworks.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krishworks.countries.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel : ViewModel() {

    private lateinit var countries: MutableLiveData<List<Country>>
    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun getCountryList(): MutableLiveData<List<Country>> {
        if (!::countries.isInitialized) {
            countries = MutableLiveData()
            this.fetchCountryList()
        }
        return countries
    }

    fun refresh() {
        fetchCountryList()
    }

    private fun fetchCountryList() {
        loading.value = true

        val countriesApi = CountryServiceBuilder.buildService(CountryService::class.java)

        val requestCall = countriesApi.getCountryList()

        requestCall.enqueue(object : Callback<List<Country>> {

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                loading.value = false
                countryLoadError.value = true
            }

            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                loading.value = false
                if (response.isSuccessful) {
                    val countryList = response.body()!!
                    countries.value = countryList
                    countryLoadError.value = false
                } else {
                    countryLoadError.value = true
                }
            }
        })
    }
}