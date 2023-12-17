package com.example.resisthabitapplication
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import android.content.Context
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit

//import kotlinx.coroutines.launch


class TimerModel: ViewModel()
{

    private val _currentNumber = MutableStateFlow<Int>(0)
    val currentNumber = _currentNumber.asStateFlow()

    // Wrap the current number in Data
    val workerData = Data.Builder()
        .putInt("currentNumber", 0) // You may replace 0 with a default value or the initial value of your _currentNumber
        .build()

    private val _isTimerRunning = MutableStateFlow(false)
    private var isTimerRunning: Boolean = false
    private val _jobState = MutableStateFlow<Job?>(null)
    private var job: Job? = null

    fun updateCurrentNumber(newNumber: Int) {
        _currentNumber.value = newNumber
    }



    val addTimeRequest =
        PeriodicWorkRequestBuilder<TimerWorker>(1, TimeUnit.SECONDS)
            // Additional configuration
            .setInputData(workerData)
            .build()

    //WorkManager.getInstance(context).enqueue(addTimeRequest)

    override fun onCleared() {
        // Cancel the coroutine when the ViewModel is cleared
        job?.cancel()
        super.onCleared()
    }

}