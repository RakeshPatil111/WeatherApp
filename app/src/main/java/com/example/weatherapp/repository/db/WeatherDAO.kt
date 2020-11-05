package com.example.weatherapp.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.WeatherModel

@Dao
interface WeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWeather(weather: WeatherModel)

    @Query("SELECT * FROM weathermodel")
    fun getAllWeathersFromLocal(): LiveData<List<WeatherModel>>

    @Query("SELECT * FROM weathermodel WHERE cityName LIKE :cityName")
    fun getCityByName(cityName: String): LiveData<List<WeatherModel>>

    @Query("DELETE FROM weathermodel")
    fun deleteAllRows()
}