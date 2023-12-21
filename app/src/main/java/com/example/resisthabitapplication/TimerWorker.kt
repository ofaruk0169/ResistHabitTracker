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

        // Send the updated number to the ViewModel
        val outputData= workDataOf("updatedNumber" to updatedNumber)

        return Result.success(outputData)
    }
}

