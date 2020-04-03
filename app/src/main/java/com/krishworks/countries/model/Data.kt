package com.krishworks.countries.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val countryName: String?,
    val capital: String?,
    val flagPNG: String?
)