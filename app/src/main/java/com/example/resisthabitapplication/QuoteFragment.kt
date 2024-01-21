package com.example.resisthabitapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch


class QuoteFragment : Fragment(R.layout.quote_fragment) {

    private val quoteAPIViewModel: QuoteAPIViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch quote when the fragment is created
        quoteAPIViewModel.fetchQuote()

        // Launch a coroutine to observe the quote data
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                quoteAPIViewModel.currentQuote.collect { quote ->
                    // Update your view with the quote
                    updateQuoteContent(quote)
                }
            }
        }
    }

    private fun updateQuoteContent(quote: String) {
        val quoteTextView = view?.findViewById<TextView>(R.id.tvQuoteFragment)
        quoteTextView?.text = quote
    }
}
