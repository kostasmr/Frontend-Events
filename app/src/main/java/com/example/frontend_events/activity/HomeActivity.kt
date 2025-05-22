package com.example.frontend_events.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_events.Adapter
import com.example.frontend_events.R
import com.example.frontend_events.models.Popular

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val events = listOf(
            Popular(R.drawable.popular1, "Going to a Rock Concert", "Thursday 10 July", "Athens", "$3"),
            Popular(R.drawable.popular2, "Food Festival", "Friday 11 July", "Thessaloniki", "$5"),
        )

        val adapter = Adapter(events)
        recyclerView.adapter = adapter
    }
}