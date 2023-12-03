package com.example.resisthabitapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get information from the TimerModel class and put it into the views below

        val timeElapsed = findViewById<TextView>(R.id.streakTimeMinutes)



        //create a callback to update the UI
        val updateUICallback: (Int) -> Unit = { currentStreak ->
            timeElapsed.text = "Current Streak: $currentStreak"
        }
        //I want the timer function to start here
        var timerStart = TimerAndAPIViewModel(updateUICallback)
        timerStart.startTimer()





        //make the value above equal to the function called by the TimerClass


    }
}