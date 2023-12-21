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

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext: Context = application.applicationContext

    private val _currentNumber = MutableStateFlow<Int>(0)
    val currentNumber = _currentNumber.asStateFlow()

    // Wrap the current number in Data
    private val workerData = Data.Builder()
        .putInt(
            "currentNumber",
            _currentNumber.value
        ) // You may replace 0 with a default value or the initial value of your _currentNumber
        .build()

    private var isTimerRunning: Boolean = false

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

            WorkManager.getInstance(appContext)
                .getWorkInfoByIdFlow(addTimeRequest.id)
                .flowOn(Dispatchers.Default) // Set the dispatcher for downstream operations
                .collect { workInfo ->
                    // Process the workInfo on the specified dispatcher
                    // Handle the work result
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            // Work completed successfully
                            val outputData = workInfo.outputData
                            // Process the output data if needed
                            //use the updated number function to update the number. Doing this tomorrow.
                        }

                        WorkInfo.State.FAILED -> {
                            // Work failed
                            val failureReason = "fail"
                            // Handle the failure
                        }
                        // Handle other states as needed
                        // WorkInfo.State.RUNNING, WorkInfo.State.CANCELLED, etc.
                        else -> {
                            // Handle any unexpected states
                        }
                    }
                }
        }
    }

}