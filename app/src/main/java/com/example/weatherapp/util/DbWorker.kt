package com.example.weatherapp.util

import android.content.Context
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.weatherapp.repository.db.WeatherDb

class DbWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted parameters: WorkerParameters
) : Worker(context, parameters) {

    override fun doWork(): Result {
        return try {
            val db = WeatherDb.getInstance(applicationContext)
            db.getDAO().deleteAllRows()
            Result.success()
        } catch (throwable: Throwable) {
            Log.e("Error ::", throwable.localizedMessage)
            Result.failure()
        }
    }
}