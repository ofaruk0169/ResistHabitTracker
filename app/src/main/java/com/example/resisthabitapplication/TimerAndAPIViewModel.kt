package com.example.resisthabitapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*


class TimerAndAPIViewModel: ViewModel()
{

    public val currentNumber: MutableLiveData<Int> by lazy{
        MutableLiveData<Int>().apply {
            value = 0
        }
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

                    currentNumber.postValue(currentNumber.value?.plus(1))

                    delay(1000)
                }
            }
        }
        // else the timer should continue as normal.
    }






}