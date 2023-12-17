package com.example.resisthabitapplication

import android.content.Context
import androidx.work.WorkerParameters


class TimerWorker(appContext: Context, workerParams: WorkerParameters) {
    fun doWork() {
        //Inside doWork() I need to do the counting functionality.
        // I may have to update isTimerRunning & job as flow
        // Indicate whether the work finished successfully with the Result
        // in my case, if i was able to increment the counter successfully.













        /*
        if (!isTimerRunning) {
            isTimerRunning = true
            job = viewModelScope.launch {
                try {
                    while (isActive) {
                        _currentNumber.value = _currentNumber.value + 1
                        delay(1000)

                        return Result.success()
                    }
                } finally {
                    // This block is executed when the coroutine is cancelled
                    isTimerRunning = false
                }
            }
        }

         */
    }
}
