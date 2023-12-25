package com.example.resisthabitapplication

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.observeOn
import java.util.concurrent.TimeUnit

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    //App's context is here
    private val appContext: Context = application.applicationContext
    //Variables and functions related to number.
    private val _currentNumber = MutableStateFlow<Int>(6)
    val currentNumber = _currentNumber.asStateFlow()

    // Wrap the current number in Data
    private var workerData = createWorkerData()

    private fun createWorkerData(): Data {
        return Data.Builder()
            .putInt(
                "currentNumber",
                _currentNumber.value
            )
            .build()
    }

    private fun updateWorkerData() {
        workerData = createWorkerData()
    }

    private var isTimerRunning: Boolean = false

    private fun updateCurrentNumber(newNumber: Int) {
        _currentNumber.value = newNumber
    }
   //Variables and functions related to number.



    suspend fun startTimer() {
        if (!isTimerRunning) {
                isTimerRunning = true

            coroutineScope {

                val addTimeRequest =
                    PeriodicWorkRequestBuilder<TimerWorker>(
                        1,
                        TimeUnit.SECONDS
                    ).setInputData(workerData)
                        .build()

                // Enqueue the work
                WorkManager.getInstance(appContext)
                    .enqueue(addTimeRequest)

/*
                WorkManager.getInstance(appContext)
                    .getWorkInfoByIdFlow(addTimeRequest.id)
                    .flowOn(Dispatchers.Default) // Set the dispatcher for downstream operations
                    .collect { workInfo ->
                        Log.d("TimerViewModel", "Received WorkInfo: ${workInfo.state}")

                        // Process the workInfo on the specified dispatcher
                        // Handle the work result
                        when (workInfo.state) {

                            WorkInfo.State.SUCCEEDED -> {
                                // Work completed successfully
                                val outputData = workInfo.outputData
                                val updatedNumber = outputData.getInt("updatedNumber", 0)
                                Log.d("TimerViewModel", "Work succeeded. Updated Number: $updatedNumber")

                                // Process the output data if needed
                                // use the updated number function to update the number
                                updateCurrentNumber(updatedNumber)
                            }


                            WorkInfo.State.ENQUEUED -> {
                                // Work is enqueued but not yet running
                                Log.d("TimerViewModel", "Work is enqueued but not yet running")
                            }

                            WorkInfo.State.RUNNING -> {
                                // Work is currently running
                                Log.d("TimerViewModel", "Work is currently running")
                            }
                            // WorkInfo.State.CANCELLED
                            else -> {
                                // Handle any unexpected states
                            }
                        }
                        updateWorkerData()
                    }

  */

            }
        }
    }
}

