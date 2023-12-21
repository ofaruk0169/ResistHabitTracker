package com.example.resisthabitapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.work.WorkManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timerViewModel = ViewModelProvider(this)[TimerViewModel::class.java]
        val timeElapsedView = findViewById<TextView>(R.id.streakTimeMinutes)



        // Observe changes to the currentNumber property in the ViewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                timerViewModel.currentNumber.collect { count ->
                    // Update the UI with the current count
                    timeElapsedView.text = count.toString()
                }
            }
        }

        // Start the timer when the activity is created

        lifecycleScope.launch {
            timerViewModel.startTimer()
        }
    }
}





