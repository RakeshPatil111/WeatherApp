package com.example.weatherapp.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.util.Constant.DATABASE_NAME

@Database(entities = [WeatherModel::class], version = 4, exportSchema = false)
abstract class WeatherDb : RoomDatabase() {
    abstract fun getDAO(): WeatherDAO

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: WeatherDb? = null

        fun getInstance(context: Context): WeatherDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): WeatherDb {
            return Room.databaseBuilder(context, WeatherDb::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}