package com.example.weatherapp.util

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.weatherapp.repository.db.WeatherDb
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DbWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted parameters: WorkerParameters
) : Worker(context, parameters) {

    override fun doWork(): Result {
        return try {
            val db = WeatherDb.getInstance(applicationContext)
            db.getDAO().deleteAllRows()
            Result.success()
        } catch (throwable: Throwable) {
            Log.e("Error ::", throwable.localizedMessage ?: "Unknown Error")
            Result.failure()
        }
    }
}