package com.example.weatherapp.util

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherapp.model.WeatherModel

class CityDiffUtil : DiffUtil.ItemCallback<WeatherModel>() {
    override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
        return oldItem == newItem
    }

}