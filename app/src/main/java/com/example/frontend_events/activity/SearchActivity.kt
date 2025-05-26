package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_events.R
import com.example.frontend_events.SearchAdapter
import com.example.frontend_events.models.Event

class SearchActivity : AppCompatActivity() {

    private lateinit var allEvents: List<Event>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerView = findViewById(R.id.search_list)
        searchEditText = findViewById(R.id.search_text)

        val query = intent.getStringExtra("query") ?: ""
        searchEditText.setText(query)

        allEvents = listOf(
            Event(
                R.drawable.popular1,
                "Going to a Rock Concert",
                "Thursday 10 July",
                "Athens",
                "$03.00",
                "We have a team but still missing a couple of people.Let's play together! We have a team but still missing a couple of people. Let's play together! We have a team but still missing a couple of people.",
                "Nikos Minos"
            ),
            Event(R.drawable.popular2,
                "Food Festival",
                "Friday 11 July",
                "Thessaloniki",
                "$05.00",
                "We have a team but still missing a couple of people.Let's play together! We have a team but still missing a couple of people. Let's play together! We have a team but still missing a couple of people.",
                "John Made"
            ),
            Event(R.drawable.img_recom1,
                "Food Festival",
                "Friday 11 July",
                "Thessaloniki",
                "$05.00",
                "We have a team but still missing a couple of people.Let's play together! We have a team but still missing a couple of people. Let's play together! We have a team but still missing a couple of people.",
                "John Made"
            ),
            Event(R.drawable.img_recom2,
                "Food Festival",
                "Friday 11 July",
                "Thessaloniki",
                "$05.00",
                "We have a team but still missing a couple of people.Let's play together! We have a team but still missing a couple of people. Let's play together! We have a team but still missing a couple of people.",
                "John Made"
            ),
        )

        val filtered = allEvents.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.location.contains(query, ignoreCase = true) ||
                    it.date.contains(query, ignoreCase = true)
        }

        if (filtered.isEmpty()) {
            val noResultsText = findViewById<TextView>(R.id.noResultsText)
            noResultsText.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SearchAdapter(filtered.toMutableList())
        recyclerView.adapter = adapter

        fun filterEvents(query: String) {
            val filtered = allEvents.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.location.contains(query, ignoreCase = true) ||
                        it.date.contains(query, ignoreCase = true)
            }

            adapter.updateData(filtered)

            val noResultsText = findViewById<TextView>(R.id.noResultsText)
            noResultsText.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                filterEvents(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }
}