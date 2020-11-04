package com.example.weatherapp.ui.fragment

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.weatherapp.R
import com.example.weatherapp.ui.MainActivity
import com.example.weatherapp.util.testWeather
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CityListFragmentTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun jumpToPlantDetailFragment() {
        activityTestRule.activity.apply {
            runOnUiThread {
                val bundle = Bundle().apply { putSerializable("weather", testWeather) }
                findNavController(R.id.nav_host_fragment).navigate(R.id.detailsFragment, bundle)
            }
        }
    }
}