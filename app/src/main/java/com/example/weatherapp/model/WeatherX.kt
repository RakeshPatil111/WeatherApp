package com.example.weatherapp.model


import com.google.gson.annotations.SerializedName

data class WeatherX(
    @SerializedName("description")
    var description: String,
    @SerializedName("icon")
    var icon: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("main")
    var main: String
)