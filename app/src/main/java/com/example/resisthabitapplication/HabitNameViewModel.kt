package com.example.resisthabitapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HabitNameViewModel : ViewModel() {

    private val _habitName = MutableStateFlow<String>("")
    val habitName: StateFlow<String> get() = _habitName




    fun changeName() {








    }




}