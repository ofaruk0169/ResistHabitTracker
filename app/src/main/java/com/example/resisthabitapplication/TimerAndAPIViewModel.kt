package com.example.resisthabitapplication

import kotlinx.coroutines.*


class TimerAndAPIViewModel(private val callback: (Int) -> Unit)
{

    private var timerCallback: ((Int) -> Unit)? = null

    fun setTimerCallback(callback: (Int) -> Unit) {
        timerCallback = callback
    }

    public var isTimerRunning: Boolean = false
    public var currentTime = 0
    private var job: Job? = null


    var currentNumber: Int = 0

    fun startTimer() {
        // if the timer is not already running, start the timer
        if (!isTimerRunning) {
            isTimerRunning = true
            job = CoroutineScope(Dispatchers.Main).launch {
                while (isActive) {
                    // Perform timer-related operations here
                    currentNumber++
                    // Log or update UI with the currentStreak value
                    callback.invoke(currentNumber)
                    // Delay for 1 second
                    delay(1000)
                }
            }
        }
        // else the timer should continue as normal.
    }

    fun updateCount() {

    }


}