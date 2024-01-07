package com.example.resisthabitapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import io.ktor.http.isSuccess
import org.json.JSONArray

class QuoteAPIViewModel : ViewModel() {

    private val _currentQuote = MutableStateFlow<String>("")
    val currentQuote: StateFlow<String> get() = _currentQuote

    fun fetchQuote() {
        // Use Ktor to make API request
        val client = HttpClient()
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response: HttpResponse = client.get("https://zenquotes.io/api/today")

                if (response.status.isSuccess()) {

                    val responseBody = response.bodyAsText()
                    //_currentQuote.value = responseBody

                    // Parse the JSON response
                    val jsonArray = JSONArray(responseBody)
                    if (jsonArray.length() > 0) {
                        val jsonObject = jsonArray.getJSONObject(0)

                        // Access individual values
                        val quote = jsonObject.getString("q")
                        val author = jsonObject.getString("a")
                        val htmlFormattedQuote = jsonObject.getString("h")

                        val fullQuote = "$quote - $author"

                        // Assign the quote to your variable or use it as needed
                        _currentQuote.value = fullQuote

                    }

                } else {
                    // Handle non-successful response
                    _currentQuote.value = "Failed to fetch quote. Status code: ${response.status.value}"
                }
            } catch (e: Exception) {
                // Handle error
                _currentQuote.value = "Failed to fetch quote. Error: ${e.message}"
            } finally {
                client.close()
            }
        }
    }
}


