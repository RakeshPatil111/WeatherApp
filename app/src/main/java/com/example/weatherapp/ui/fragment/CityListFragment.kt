package com.example.weatherapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCityListBinding
import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.ui.MainActivity
import com.example.weatherapp.ui.adapter.CityRecyclerAdapter
import com.example.weatherapp.ui.adapter.SearchViewAdapter
import com.example.weatherapp.util.Constant
import com.example.weatherapp.util.Resource
import com.example.weatherapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_city_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CityListFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    lateinit var searchCityAdapter: SearchViewAdapter
    lateinit var cityAdapter: CityRecyclerAdapter
    lateinit var weatherList: List<WeatherModel>
    private lateinit var weather: WeatherModel

    private lateinit var binding: FragmentCityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchCityAdapter = SearchViewAdapter()
        cityAdapter = CityRecyclerAdapter()
        weatherList = listOf()
        // Get View Model
        viewModel = (activity as MainActivity).weatherViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCityListBinding.inflate(inflater, container, false)

        // set on click
        binding.cvSearchCity.setOnClickListener {
            gotoDetailsFragment()
        }

        //Observe Data Change
        setObserver()

        // set recycler view
        setRecyclerView()

        return binding.root
    }

    private fun setRecyclerView() {
        // set recycler view
        binding.rvCity.adapter = cityAdapter
        cityAdapter.setOnItemCLickListener {
            weather = it
            gotoDetailsFragment()
        }
    }

    private fun gotoDetailsFragment() {
        val bundle = Bundle()
        bundle.putSerializable("weather", weather)
        findNavController().navigate(R.id.action_cityListFragment_to_detailsFragment, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add text change listener
        var searchJob: Job? = null

        etCitySearch.addTextChangedListener { editable ->
            searchJob?.cancel()
            searchJob = MainScope().launch {
                delay(Constant.SEARCH_TIME_DELAY)
                editable?.let {
                    if (!editable.toString().trim().isEmpty()) {
                        viewModel.getCityByName(editable.toString()).observe(viewLifecycleOwner,
                            Observer {
                                if (it.isNotEmpty()) {
                                    cityAdapter.submitList(it)
                                } else {
                                    viewModel.getWeather(editable.toString())
                                }
                            })

                    } else {
                        // No text for serach, display list
                        cityAdapter.submitList(weatherList)
                        cvSearchCity.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setObserver() {
        /*
        View model observer
        Get city searched from API
        Set name of city if city found
        else set error message to text view/snackbar
         */
        viewModel.weatherLiveData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { weatherResponse ->

                        weather = WeatherModel(
                            weatherResponse.name,
                            weatherResponse.weather.get(0).description,
                            weatherResponse.weather.get(0).icon,
                            weatherResponse.main.temp,
                            weatherResponse.main.tempMin,
                            weatherResponse.main.tempMax,
                            weatherResponse.main.humidity,
                            weatherResponse.main.feelsLike,
                            System.currentTimeMillis()
                        )
                        binding.tvSearchedCity.text = weatherResponse.name
                        binding.cvSearchCity.visibility = View.VISIBLE
                    }
                }
                is Resource.Loading -> {
                    binding.cvSearchCity.visibility = View.GONE
                    Snackbar.make(requireView(), "Getting weather...", Snackbar.LENGTH_SHORT).show()
                }

                is Resource.Error -> {
                    binding.tvSearchedCity.text = "Error: Nothing Found"
                    Snackbar.make(requireView(), response.message!!, Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.localWeatherLiveData.observe(viewLifecycleOwner, Observer { weatherResponseList ->
            if (weatherResponseList.isNotEmpty()) {
                weatherList = weatherResponseList
                cityAdapter.submitList(weatherList)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.etCitySearch.setText("")
        binding.cvSearchCity.visibility = View.GONE
    }
}