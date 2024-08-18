package com.example.summaryweather.logic.model

import com.google.gson.annotations.SerializedName

data class Place(val name: String, val location: Location,
    @SerializedName("formatted_adress") val address: String)