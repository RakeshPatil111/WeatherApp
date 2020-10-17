package com.example.weatherapp.util

import java.text.SimpleDateFormat
import java.util.*

object Util {
    // Get Celsius Temp from F
    fun ferToCelsius(tempInF: Double): String {
        var tempInC = tempInF - 273.15
        return String.format("%.1f", tempInC) + "\u2103"
    }

    // Get icon url
    fun getIconURLForWeather(icon: String) = "http://openweathermap.org/img/w/$icon.png"

    fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:MM");
        val dateString = formatter.format(Date(System.currentTimeMillis()))
        return dateString
    }
}