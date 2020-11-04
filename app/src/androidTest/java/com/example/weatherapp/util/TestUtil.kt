package com.example.weatherapp.util

import com.example.weatherapp.model.WeatherModel

private val weatherModel1 = WeatherModel(
    1, "City 1", "Sunny", "",
    242.0, 140.25, 305.20, 75, 153.2, System.currentTimeMillis()
)


val weatherList = arrayListOf<WeatherModel>(
    WeatherModel(
        1, "City 1", "Sunny", "",
        242.0, 140.25, 305.20, 75, 153.2, System.currentTimeMillis()
    ),
    WeatherModel(
        2, "City 2", "Sunny", "",
        242.0, 140.25, 305.20, 75, 153.2, System.currentTimeMillis()
    ),
    WeatherModel(
        3, "City 3", "Sunny", "",
        242.0, 140.25, 305.20, 75, 153.2, System.currentTimeMillis()
    )
)

val testWeather = weatherList[0]