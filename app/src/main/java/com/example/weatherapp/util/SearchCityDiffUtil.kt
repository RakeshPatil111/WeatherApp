package com.example.weatherapp.util

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherapp.model.Weather

class SearchCityDiffUtil : DiffUtil.ItemCallback<Weather>() {
    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem == newItem
    }

}