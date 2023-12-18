package com.example.resisthabitapplication
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf


class TimerWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Do the background work here
        val currentNumber = inputData.getInt("currentNumber", 0)


        //update number here
        val updatedNumber = currentNumber + 1

        val outputData= workDataOf("updatedNumber" to updatedNumber)


        //timerViewModel.updateCurrentNumber(currentNumber)
        // Indicate whether the work was successful
        return Result.success(outputData)
    }
}



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