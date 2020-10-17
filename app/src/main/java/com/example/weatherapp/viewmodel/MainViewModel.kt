package com.example.weatherapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MainViewModel(val repository: WeatherRepository, application: Application) :
    AndroidViewModel(application) {

    val weatherLiveData: MutableLiveData<Resource<Weather>> = MutableLiveData()
    var weatherResponse: Weather? = null
    var localWeatherLiveData: LiveData<List<WeatherModel>>

    init {
        localWeatherLiveData = repository.getAllLocalWeather()
    }

    fun upsertWeather(weather: WeatherModel) = viewModelScope.launch {
        repository.upsertWeather(weather)
    }

    fun getCityByName(cityName: String) = repository.getCityByName("$cityName%")

    // Get data from Server
    fun getWeather(cityName: String) = viewModelScope.launch {
        weatherLiveData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = repository.getWeather(cityName)
                weatherLiveData.postValue(handleResponse(response))
            } else {
                // If we want to show error message for connection
                weatherLiveData.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                // Unknown error for type conversion may occur
                // if so check with model and response
                is IOException -> weatherLiveData.postValue(Resource.Error("Network Failure"))
                else -> {
                    Log.i("Erro :: ", t.localizedMessage)
                    weatherLiveData.postValue(Resource.Error(t.localizedMessage))
                }
            }
        }
    }

    private fun handleResponse(response: Response<Weather>): Resource<Weather> {
        // Check repsonse is successful
        if (response.isSuccessful && response.code() == 200) {
            response.body()?.let { apiResponse ->
                weatherResponse = apiResponse
                return Resource.Success(weatherResponse!!)
            }
        }
        return Resource.Error("Error")
    }

    // Check internet
    private fun hasInternetConnection(): Boolean {

        val connectivityManager = getApplication<WeatherApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    else -> false
                }
            }
        }
        return false
    }
}