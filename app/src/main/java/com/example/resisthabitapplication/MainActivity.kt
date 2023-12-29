package com.example.resisthabitapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.activity.viewModels


class MainActivity : AppCompatActivity() {

    private val timerViewModel: TimerViewModel by viewModels()

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

    }
}
