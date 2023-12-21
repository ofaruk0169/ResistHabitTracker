package com.example.resisthabitapplication
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.observeOn
import java.util.concurrent.TimeUnit

//import kotlinx.coroutines.launch



class TimerViewModel(application: Application) : AndroidViewModel(application) {


private val appContext: Context = application.applicationContext




private val _currentNumber = MutableStateFlow<Int>(0)
    val currentNumber = _currentNumber.asStateFlow()

    // Wrap the current number in Data
    val workerData = Data.Builder()
        .putInt(
            "currentNumber",
            _currentNumber.value
        ) // You may replace 0 with a default value or the initial value of your _currentNumber
        .build()

    private var isTimerRunning: Boolean = false
    //private var job: Job? = null

    private fun updateCurrentNumber(newNumber: Int) {
        _currentNumber.value = newNumber
    }

        suspend fun startTimer() {
            if (!isTimerRunning) {

                isTimerRunning = true

                val addTimeRequest =
                    PeriodicWorkRequestBuilder<TimerWorker>(1, TimeUnit.SECONDS)
                        .setInputData(workerData)
                        .build()

                // Enqueue the work
                WorkManager.getInstance(appContext).enqueue(addTimeRequest)

                // Observe the work result
                WorkManager.getInstance(appContext)
                    .getWorkInfoByIdFlow(addTimeRequest.id)
                    .flowOn(Dispatchers.Default)
                    .collect { workerInfo ->
                        // Handle the work result
                        when (workerInfo.state) {
                            WorkInfo.State.SUCCEEDED -> {
                                // Work completed successfully
                                val outputData = workerInfo.outputData
                                val newNumber = outputData.getInt("newNumber", 0)
                                // Process the output data here
                                updateCurrentNumber(newNumber)
                            }

                            WorkInfo.State.FAILED -> {
                                // Work failed
                                val failureMessage =
                                    workerInfo.outputData.getString("KEY_FAILURE_MESSAGE")
                                // Handle the failure
                            }
                            // Handle other states as needed
                            // WorkInfo.State.RUNNING, WorkInfo.State.CANCELLED, etc.
                            else -> {}
                        }
                    }
            }
        }
    }



/*
override fun onCleared() {
    // Cancel the coroutine when the ViewModel is cleared
    job?.cancel()
    super.onCleared()
}

 */