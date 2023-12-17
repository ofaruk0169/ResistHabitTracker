package com.example.resisthabitapplication

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters



class TimerWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        // Do the background work here
        val currentNumber = inputData.getInt("currentNumber", 0)




        (applicationContext as? ResistHabitApplication)?.TimerModel?.updateCurrentNumber(currentNumber)



        // Indicate whether the work was successful
        return Result.success()
    }
}
