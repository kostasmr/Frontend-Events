package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_events.Adapter
import com.example.frontend_events.RecomAdapter
import com.example.frontend_events.R
import com.example.frontend_events.SearchAdapter
import com.example.frontend_events.models.Event

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recyclerView = findViewById<RecyclerView>(R.id.popular_list)
        val recom_list = findViewById<RecyclerView>(R.id.recom_list)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recom_list.layoutManager = LinearLayoutManager(this)


        val events = listOf(
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

        val popular_events = listOf(events[0],events[1])
        val recom_events = listOf(events[2],events[3])

        val adapter = Adapter(popular_events) { selectedEvent ->
            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("event", selectedEvent)
            intent.putExtra("origin", "home")
            startActivity(intent)
        }
        val recomAdapter = RecomAdapter(recom_events) { selectedEvent ->
            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("event", selectedEvent)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        recom_list.adapter = recomAdapter

        val searchEditText = findViewById<EditText>(R.id.search_input)

        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                val query = searchEditText.text.toString()
                val intent = Intent(this, SearchActivity::class.java)
                intent.putExtra("query", query)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                searchEditText.setText("")
                true
            } else {
                false
            }
        }

    }
}

