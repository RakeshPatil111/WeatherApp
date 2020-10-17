package com.example.weatherapp.repository.service

import com.example.weatherapp.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("/data/2.5/weather")
    suspend fun getWeather(@Query("q") city: String, @Query("appid") key: String):
            Response<Weather>
}