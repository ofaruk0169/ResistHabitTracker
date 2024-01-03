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

    private var startTimeMillis: Long = 0

    init {
        // Initialize _currentNumber with the value from SavedStateHandle or SharedPreferences
        _currentNumber.value =
            savedStateHandle["currentNumber"] ?: sharedPreferences.getInt("currentNumber", 0)

        // If there's a saved start time, calculate the time elapsed since then
        val savedStartTimeMillis = sharedPreferences.getLong("start_time_millis", 0)
        if (savedStartTimeMillis > 0) {
            val elapsedTimeSeconds = ((System.currentTimeMillis() - savedStartTimeMillis) / 1000).toInt()
            _currentNumber.value += elapsedTimeSeconds
        }

        // Start the timer
        startTimer()
    }

    private fun updateCurrentNumber(newNumber: Int) {
        _currentNumber.value = newNumber

        // Save the current number and start time to both SavedStateHandle and SharedPreferences
        savedStateHandle["currentNumber"] = newNumber
        sharedPreferences.edit()
            .putInt("currentNumber", newNumber)
            .putLong("start_time_millis", startTimeMillis)
            .apply()
    }

    private var isTimerRunning: Boolean = false
    private var job: Job? = null

    fun startTimer() {
        // If the timer is not already running, start the timer
        if (!isTimerRunning) {
            isTimerRunning = true
            startTimeMillis = System.currentTimeMillis()

            job = viewModelScope.launch {
                try {
                    while (isActive) {
                        val elapsedTimeSeconds = ((System.currentTimeMillis() - startTimeMillis) / 1000).toInt()
                        _currentNumber.value += 1
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
