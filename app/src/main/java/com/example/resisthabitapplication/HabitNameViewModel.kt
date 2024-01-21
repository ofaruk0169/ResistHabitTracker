package com.example.resisthabitapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HabitNameViewModel : ViewModel() {

    private val _currentQuote = MutableStateFlow<String>("")
    val currentQuote: StateFlow<String> get() = _currentQuote




}