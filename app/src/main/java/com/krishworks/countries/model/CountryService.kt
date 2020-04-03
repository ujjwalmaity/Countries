package com.krishworks.countries.model

import retrofit2.Call
import retrofit2.http.GET

interface CountryService {

    @GET("ujjwalmaity/CountriesApi/master/countriesV1.json")
    fun getCountryList(): Call<List<Country>>
}