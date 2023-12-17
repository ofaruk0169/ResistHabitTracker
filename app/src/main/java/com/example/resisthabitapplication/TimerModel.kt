package com.example.resisthabitapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import androidx.lifecycle.viewModelScope
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

    //WorkManager.getInstance().enqueue(addTimeRequest)






  /*
    fun startTimer() {
        // if the timer is not already running, start the timer
        if (!isTimerRunning) {
            isTimerRunning = true
            job = viewModelScope.launch {
                try {
                    while (isActive) {
                        _currentNumber.value = _currentNumber.value + 1
                        delay(1000)
                    }
                } finally {
                    // This block is executed when the coroutine is cancelled
                    isTimerRunning = false
                }
            }
        }
        // else the timer should continue as normal.
    }
   */

    //Do the work request part 2 and 3 here.


    override fun onCleared() {
        // Cancel the coroutine when the ViewModel is cleared
        job?.cancel()
        super.onCleared()
    }

}