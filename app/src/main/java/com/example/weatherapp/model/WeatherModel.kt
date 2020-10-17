package com.example.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class WeatherModel(
    val cityName: String,
    val description: String,
    val icon: String,
    val temp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val humidity: Int,
    val feelsLike: Double,
    val createdAt: Long
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}