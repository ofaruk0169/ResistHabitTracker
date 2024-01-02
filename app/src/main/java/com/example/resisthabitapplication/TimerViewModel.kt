package com.example.resisthabitapplication

import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TimerViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _currentNumber = MutableStateFlow<Int>(0)
    val currentNumber: StateFlow<Int> get() = _currentNumber.asStateFlow()

    init {
        // Initialize _currentNumber with the value from SavedStateHandle or SharedPreferences
        _currentNumber.value =
            savedStateHandle.get("currentNumber") ?: sharedPreferences.getInt("currentNumber", 0)
    }

    private fun updateCurrentNumber(newNumber: Int) {
        _currentNumber.value = newNumber

        // Save the current number to both SavedStateHandle and SharedPreferences
        savedStateHandle["currentNumber"] = newNumber
        sharedPreferences.edit().putInt("currentNumber", newNumber).apply()
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
