package com.example.weatherapp.repository

import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.repository.db.WeatherDb
import com.example.weatherapp.repository.service.RetrofitClient
import com.example.weatherapp.util.Constant

class WeatherRepository(val db: WeatherDb) {

    suspend fun getWeather(cityName: String) =
        RetrofitClient.api.getWeather(cityName, Constant.API_ID)

    suspend fun upsertWeather(weather: WeatherModel) = db.getDAO().upsertWeather(weather)

    fun getAllLocalWeather() = db.getDAO().getAllWeathersFromLocal()

    fun getCityByName(cityName: String) = db.getDAO().getCityByName(cityName)
}