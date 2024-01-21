package com.example.resisthabitapplication

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class HabitNameFragment : Fragment(R.layout.name_fragment) {

    private lateinit var sharedPreferences: SharedPreferences
    private val HABIT_NAME_KEY = "habit_name_key"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameTextView = view.findViewById<TextView>(R.id.tvNameFragment)

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        // Retrieve the habit name from SharedPreferences
        val savedHabitName = sharedPreferences.getString(HABIT_NAME_KEY, "Click to change name")
        nameTextView.text = savedHabitName

        // Set an OnClickListener to the TextView
        nameTextView.setOnClickListener {
            showInputDialog()
        }
    }

    private fun showInputDialog() {
        // Show an input dialog or a custom view to get the new text from the user
        // AlertDialog with an EditText

        val editText = EditText(requireContext())
        editText.hint = "E.g. Smoking, drinking, fast food etc."

        AlertDialog.Builder(requireContext())
            .setTitle("Change Habit Name")
            .setView(editText)
            .setPositiveButton("OK") { _, _ ->
                val newText = editText.text.toString()
                updateTextContent(newText)

                // Save the habit name in SharedPreferences
                sharedPreferences.edit().putString(HABIT_NAME_KEY, newText).apply()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateTextContent(newText: String) {
        val nameTextView = view?.findViewById<TextView>(R.id.tvNameFragment)
        nameTextView?.text = newText
    }
}
