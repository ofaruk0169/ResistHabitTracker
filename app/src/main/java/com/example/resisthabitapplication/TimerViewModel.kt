package com.example.resisthabitapplication

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TimerViewModel(private val savedStateHandle: SavedStateHandle): ViewModel()
{
    private val _currentNumber = MutableStateFlow<Int>(0)
    val currentNumber = _currentNumber.asStateFlow()

    private var isTimerRunning: Boolean = false
    private var job: Job? = null

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

    override fun onCleared() {
        // Cancel the coroutine when the ViewModel is cleared
        job?.cancel()
        super.onCleared()
    }

}