package com.example.resisthabitapplication

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TimerViewModel(
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val _currentNumber = MutableStateFlow<Int>(savedStateHandle["currentNumber"] ?: 0)
    val currentNumber = _currentNumber.asStateFlow()

    init {
        // Log the initial value for debugging
        Log.d("TimerViewModel", "Initial Value: ${_currentNumber.value}")

        // Initialize _currentNumber with the value from SavedStateHandle
        _currentNumber.value = savedStateHandle["currentNumber"] ?: 0

        // Log the restored value for debugging
        Log.d("TimerViewModel", "Restored Value: ${_currentNumber.value}")
    }

    private fun updateCurrentNumber(newNumber: Int) {
        _currentNumber.value = newNumber
        savedStateHandle["currentNumber"] = newNumber
        Log.d("TimerViewModel", "Saved currentNumber: $newNumber")
    }

    private fun displaySaved() {
        for (key in savedStateHandle.keys()) {
            val value: Int? = savedStateHandle.get(key)
            Log.d("SavedStateHandle", "$key: $value")
        }
    }

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
                        updateCurrentNumber(_currentNumber.value)
                        displaySaved()
                        delay(1000)
                    }
                } finally {
                    // This block is executed when the coroutine is cancelled
                    isTimerRunning = false
                }
            }
        } else {
            job = viewModelScope.launch {
                try {
                    while (isActive) {
                        _currentNumber.value = _currentNumber.value + 1
                        updateCurrentNumber(_currentNumber.value)
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