package com.example.resisthabitapplication
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay


class TimerWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("TimerWorker", "doWork called")

        try {

            delay(5000)
            // Your existing code for updating the number
            val currentNumber = inputData.getInt("currentNumber", 0)
            val updatedNumber = currentNumber + 1
            val outputData = workDataOf("updatedNumber" to updatedNumber)
            Log.d("TimerWorker", "Updated Number: $updatedNumber")

            Log.d("TimerWorker", "Returning Result.success(outputData)")

            return Result.success(outputData)
        } catch (e: Exception) {
            Log.e("TimerWorker", "Error in doWork: ${e.message}")
            return Result.failure()
        }
    }







}


