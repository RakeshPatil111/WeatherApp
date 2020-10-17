package com.example.weatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.weatherapp.R
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.repository.db.WeatherDb
import com.example.weatherapp.viewmodel.MainViewModel
import com.example.weatherapp.viewmodel.provider.MainViewModelProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var weatherViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialise View Model
        val db = WeatherDb.getInstance(this)
        val repository = WeatherRepository(db)
        val viewModelProvider = MainViewModelProvider(repository, application)
        weatherViewModel = ViewModelProvider(this, viewModelProvider).get(MainViewModel::class.java)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

    }
}