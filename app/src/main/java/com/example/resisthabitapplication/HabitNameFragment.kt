package com.example.resisthabitapplication

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch


class HabitNameFragment : Fragment(R.layout.name_fragment) {

    private lateinit var dataStore: DataStore<Preferences>

    private companion object {
        val HABIT_NAME = stringPreferencesKey("habit_name")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameTextView = view.findViewById<TextView>(R.id.tvNameFragment)

        // Retrieve the habit name from SharedPreferences


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

                // save the habit name in Data Store
                suspend fun saveHabitNameDataStore(newText: String) {
                    dataStore.edit { preferences ->
                        preferences[HABIT_NAME] = newText
                    }
                }

            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateTextContent(newText: String) {
        val nameTextView = view?.findViewById<TextView>(R.id.tvNameFragment)
        nameTextView?.text = newText
    }
}
