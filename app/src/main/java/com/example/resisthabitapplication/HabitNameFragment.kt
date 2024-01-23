package com.example.resisthabitapplication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import com.example.resisthabitapplication.utils.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val String.omar get() = "$this is gay"
fun String.omar2() = "gay"


class HabitNameFragment : Fragment(R.layout.name_fragment) {

    private companion object {
        val HABIT_NAME = stringPreferencesKey("habit_name")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val asdasd = "Omar".omar
            
        //dataStore = requireContext().createDataStore("habit_name_data_store")


        val nameTextView = view.findViewById<TextView>(R.id.tvNameFragment)

        // Retrieve the habit name from Data Store
        CoroutineScope(Dispatchers.Main).launch {
            val habitName = retrieveHabitNameFromDataStore()
            updateTextContent(habitName)
        }

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
                CoroutineScope(Dispatchers.Main).launch {
                    saveHabitNameDataStore(newText)
                }

            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    private suspend fun saveHabitNameDataStore(newText: String) {
        requireContext().dataStore.edit { preferences ->
            preferences[HABIT_NAME] = newText
        }
    }

    private suspend fun retrieveHabitNameFromDataStore(): String {
        return requireContext().dataStore.data.map { preferences ->
            preferences[HABIT_NAME] ?: "Edit Habit Name"
        }.first()
    }

    private fun updateTextContent(newText: String) {
        val nameTextView = view?.findViewById<TextView>(R.id.tvNameFragment)
        nameTextView?.text = newText
    }
}
