package com.example.weatherapp.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.repository.db.WeatherDb
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain
import javax.inject.Inject

@HiltAndroidTest
class MainViewModelTest {
    private lateinit var appDatabase: WeatherDb
    private lateinit var viewModel: MainViewModel
    val application = Application()
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)

    @Inject
    lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        hiltRule.inject()

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, WeatherDb::class.java).build()

        viewModel = MainViewModel(weatherRepository, application)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }
}