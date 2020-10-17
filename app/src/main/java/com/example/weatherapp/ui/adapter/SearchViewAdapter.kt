package com.example.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemSearchBinding
import com.example.weatherapp.model.Weather
import com.example.weatherapp.util.SearchCityDiffUtil

class SearchViewAdapter :
    ListAdapter<Weather, SearchViewAdapter.SearchViewHolder>(SearchCityDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemSearchBinding>(
            inflater, R.layout.item_search, parent, false
        )
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.view.weather = getItem(position)
    }

    inner class SearchViewHolder(var view: ItemSearchBinding) : RecyclerView.ViewHolder(view.root)
}