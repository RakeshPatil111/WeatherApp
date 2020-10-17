package com.example.weatherapp.di

import android.content.Context
import com.example.weatherapp.repository.db.WeatherDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = WeatherDb.getInstance(appContext)

    @Singleton
    @Provides
    fun provideWeatherDao(db: WeatherDb) = db.getDAO()
}