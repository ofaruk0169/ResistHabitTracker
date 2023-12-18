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

    private lateinit var mainViewModel: TimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //mainViewModel = ViewModelProvider(this).get(TimerViewModel::class.java)
        mainViewModel = ViewModelProvider(this)[TimerViewModel::class.java]

        //mainViewModel.startTimer()

        val timeElapsedView = findViewById<TextView>(R.id.streakTimeMinutes)

        //val worker = TimerWorker(this, streakTimeMinutes, mainViewModel)


    }
}



/*
        WorkManager
            .getInstance(this)
            .enqueue(addTimeRequest)


 */
/*
lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
        mainViewModel.currentNumber.collect { data ->
            // This block is executed when the observed data changes
            runOnUiThread {
                timeElapsedView.text = data.toString()
            }
        }
    }
}

 */