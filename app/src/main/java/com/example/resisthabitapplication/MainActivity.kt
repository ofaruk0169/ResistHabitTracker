package com.example.resisthabitapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: TimerAndAPIViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(TimerAndAPIViewModel::class.java)
        mainViewModel.startTimer()

        val timeElapsedView = findViewById<TextView>(R.id.streakTimeMinutes)

        mainViewModel.currentNumber.observe(this, Observer {
            timeElapsedView.text = it.toString()
        })

    }

}