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
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.observe





class MainActivity : AppCompatActivity() {

    private val timerViewModel: TimerViewModel by viewModels {
        TimerViewModelFactory(
            SavedStateHandle( /* Pass any default values if needed */ ),
            getSharedPreferences("current_streak", Context.MODE_PRIVATE)
        )
    }

    private val quoteAPIViewModel: QuoteAPIViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerViewModel.startTimer()

        val timeElapsedView = findViewById<TextView>(R.id.streakTimeMinutes)
        val daysView = findViewById<TextView>(R.id.streakDays)
        val quoteTextView = findViewById<TextView>(R.id.motivationalQuote)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                timerViewModel.currentNumber.collect { data ->
                    // This block is executed when the observed data changes
                    runOnUiThread {
                        daysView.text = "Days: ${timerViewModel.getDays()}"
                        timeElapsedView.text = timerViewModel.formatTimerValue()
                    }
                }
            }
        }



        // Observe the StateFlow and update UI accordingly
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                quoteAPIViewModel.currentQuote.collect { quote ->
                    // Update your view with the quote
                    quoteTextView.text = quote
                }
            }
        }

        // Observe the StateFlow and update UI accordingly
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                quoteAPIViewModel.currentQuote.collect { quote ->
                    // Update your view with the quote
                    quoteTextView.text = quote
                }
            }
        }



        // Find the reset button by its ID
        val resetButton = findViewById<Button>(R.id.resetButton)

        // Click listener for the reset button
        resetButton.setOnClickListener {
            resetTimer()
        }

        // Trigger the API request
        quoteAPIViewModel.fetchQuote()


        val nameFragment = NameFragment()
        val quoteFragment = QuoteFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentToggle, nameFragment)
            commit()
        }

        // Button click listeners
        findViewById<Button>(R.id.habitNameBtn).setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentToggle, nameFragment)
                commit()
            }
        }
        findViewById<Button>(R.id.quoteBtn).setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentToggle, quoteFragment)
                commit()
            }
        }

    }



    // Function to reset the timer
    private fun resetTimer() {
        // Reset the timer value to 0
        timerViewModel.resetTimer()

        // Optionally, update any UI elements or perform additional actions after reset
    }





}
