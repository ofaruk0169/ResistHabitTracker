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
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class TimerAndAPIViewModel: ViewModel()
{
    public val currentNumber: MutableLiveData<Int> by lazy{
        MutableLiveData<Int>().apply {
            value = 0
        }
    }

    val currentNumber: LiveData<Int>
        get() = currentNumber

    public var isTimerRunning: Boolean = false
    private var job: Job? = null

    fun startTimer() {
        // if the timer is not already running, start the timer
        if (!isTimerRunning) {
            isTimerRunning = true

            //here we need to use WorkManager to increment the counter by 1 every 1 second
            val periodicWorkRequest = PeriodicWorkRequestBuilder<TimerAndAPIWorker>(1, TimeUnit.SECONDS).build()

        }
        // else the timer should continue as normal.
    }
}

class TimerAndAPIWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override fun doWork(): Result {
        // Your background work goes here
        currentNumber.postValue(currentNumber.value?.plus(1))


        //indicates whether the work finished successfully with the result.
        return Result.success()
    }

}

