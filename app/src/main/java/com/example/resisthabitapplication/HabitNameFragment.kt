package com.example.resisthabitapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class HabitNameFragment : Fragment(R.layout.name_fragment) {

    private val habitNameViewModel: HabitNameViewModel by activityViewModels()


}