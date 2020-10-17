package com.example.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemWeatherBinding
import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.util.CityDiffUtil

class CityRecyclerAdapter :
    ListAdapter<WeatherModel, CityRecyclerAdapter.CityViewHolder>(CityDiffUtil()) {

    var filteredList = arrayListOf<WeatherModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemWeatherBinding>(
            inflater, R.layout.item_weather, parent, false
        )
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.view.weather = getItem(position)

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                currentList[position].let { weatherModel ->
                    it(weatherModel)
                }
            }
        }
    }

    private var onItemClickListener: ((WeatherModel) -> Unit)? = null

    fun setOnItemCLickListener(listener: ((WeatherModel) -> Unit)) {
        onItemClickListener = listener
    }

    inner class CityViewHolder(var view: ItemWeatherBinding) : RecyclerView.ViewHolder(view.root)

    /* override fun getFilter(): Filter {
         return object : Filter() {
             override fun performFiltering(constraint: CharSequence?): FilterResults {
                 var list: ArrayList<WeatherModel> = arrayListOf()
                 val charSearch = constraint.toString()
                 if (charSearch.isEmpty()) {
                     list.addAll(currentList)
                 } else {
                     val resultList = ArrayList<WeatherModel>()
                     list.addAll(currentList)
                     for (weather in list) {
                         if (weather.cityName?.toLowerCase(Locale.ROOT)!!
                                 .contains(charSearch.toLowerCase(Locale.ROOT))
                         ) {
                             resultList.add(weather)
                         }
                     }
                     filteredList = resultList
                 }
                 val filterResults = FilterResults()
                 filterResults.values = filteredList
                 return filterResults
             }

             override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                 filteredList = results?.values as ArrayList<WeatherModel>
                 submitList(filteredList)
             }
         }
     }*/
}