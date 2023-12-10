package com.example.resisthabitapplication

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class TimerAndAPIViewModel : ViewModel() {

    public val currentNumber: MutableLiveData<Int> by lazy{
        MutableLiveData<Int>().apply {
            value = 0
        }
    }

    public var isTimerRunning: Boolean = false

    private var job: Job? = null

    fun startTimer() {
        // if the timer is not already running, start the timer
        if (!isTimerRunning) {
            isTimerRunning = true

            // Pass the currentNumber value to the Worker using Data
            val data = workDataOf("currentNumber" to currentNumber.value)
            val periodicWorkRequest = PeriodicWorkRequestBuilder<TimerAndAPIWorker>(1, TimeUnit.SECONDS)
                .setInputData(data)
                .build()

            WorkManager.getInstance().enqueue(periodicWorkRequest)
        }
        // else the timer should continue as normal.
    }
}

class TimerAndAPIWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        // Retrieve the currentNumber value from the input data
        val currentNumberValue = inputData.getInt("currentNumber", 0)

        // Your background work goes here
        // Increment the currentNumber value and post it back to the ViewModel
        val updatedValue = currentNumberValue + 1

        // Create a new instance of TimerAndAPIViewModel
        val viewModel = TimerAndAPIViewModel()

        // Post the updated value to the ViewModel
        viewModel.currentNumber.postValue(updatedValue)

        // Indicates whether the work finished successfully with the result.
        return Result.success()
    }
}

