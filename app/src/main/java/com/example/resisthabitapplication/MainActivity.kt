package com.example.resisthabitapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.activity.viewModels
import androidx.lifecycle.SavedStateHandle


class MainActivity : AppCompatActivity() {

    private val timerViewModel: TimerViewModel by viewModels {
        TimerViewModelFactory(
            SavedStateHandle( /* Pass any default values if needed */ ),
            getSharedPreferences("current_streak", Context.MODE_PRIVATE)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerViewModel.startTimer()

        val timeElapsedView = findViewById<TextView>(R.id.streakTimeMinutes)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                timerViewModel.currentNumber.collect { data ->
                    // This block is executed when the observed data changes
                    runOnUiThread {
                        timeElapsedView.text = data.toString()
                    }
                }
            }
        }

        // Find the reset button by its ID
        val resetButton = findViewById<Button>(R.id.resetButton)

        // Click listener for the reset button
        resetButton.setOnClickListener {
            resetTimer()
        }

    }

    // Function to reset the timer
    private fun resetTimer() {
        // Reset the timer value to 0
        timerViewModel.resetTimer()

        // Optionally, update any UI elements or perform additional actions after reset
    }




}
