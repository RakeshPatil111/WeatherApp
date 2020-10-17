package com.example.weatherapp.viewmodel.provider

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.viewmodel.MainViewModel

class MainViewModelProvider(val repository: WeatherRepository, val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository, application) as T
    }
}