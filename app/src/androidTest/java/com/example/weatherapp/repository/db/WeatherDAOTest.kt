package com.example.weatherapp.repository.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.weatherapp.model.WeatherModel
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherDAOTest {
    private lateinit var database: WeatherDb
    private lateinit var weatherDAO: WeatherDAO

    private val weatherModel1 = WeatherModel(
        1, "City 1", "Sunny", "",
        242.0, 140.25, 305.20, 75, 153.2, System.currentTimeMillis()
    )

    private val weatherModel2 = WeatherModel(
        2, "City 2", "Sunny", "",
        242.0, 140.25, 305.20, 75, 153.2, System.currentTimeMillis()
    )

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, WeatherDb::class.java).build()
        weatherDAO = database.getDAO()

        weatherDAO.upsertWeather(weatherModel1)
        weatherDAO.upsertWeather(weatherModel2)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetWeather() {
        val weatherData = weatherDAO.getAllWeathersFromLocal()
        MatcherAssert.assertThat(weatherData.value!!.size, Matchers.equalTo(2))
        MatcherAssert.assertThat(weatherData.value!![0], Matchers.equalTo(weatherModel1))
    }

    @Test
    fun testGetWeatherByCity() {
        val weatherData = weatherDAO.getCityByName("City 1")
        MatcherAssert.assertThat(weatherData.value!!.size, Matchers.equalTo(1))
        MatcherAssert.assertThat(weatherData.value!![0], Matchers.equalTo(weatherModel1))
    }
}