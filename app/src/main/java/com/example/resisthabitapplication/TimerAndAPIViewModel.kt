package com.example.resisthabitapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*


class TimerAndAPIViewModel: ViewModel()
{

    private val currentNumber: MutableLiveData<Int> by lazy{
        MutableLiveData<Int>()
    }

    private var timerCallback: ((Int) -> Unit)? = null

    fun setTimerCallback(callback: (Int) -> Unit) {
        timerCallback = callback
    }

    public var isTimerRunning: Boolean = false
    public var currentTime = 0
    private var job: Job? = null



    //my live data from video on 3:38
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






}