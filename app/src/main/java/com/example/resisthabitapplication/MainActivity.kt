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

        // Example: Start the timer
        lifecycleScope.launch {
            // Use the timerViewModel to start the timer
            timerViewModel.startTimer()
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                timerViewModel.currentNumber.collect { data ->
                    // This block is executed when the observed data changes
                    runOnUiThread {
                        timeElapsedView.text = data.toString()
                    }
                }
            }
        }



    }
}



