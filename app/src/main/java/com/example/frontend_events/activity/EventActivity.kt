package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.frontend_events.R
import com.example.frontend_events.models.Event

class EventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        val event = intent.getSerializableExtra("event") as Event

        val imageView = findViewById<ImageView>(R.id.imageView)
        val titleView = findViewById<TextView>(R.id.title_details)
        val dateView = findViewById<TextView>(R.id.date_details)
        val locationView = findViewById<TextView>(R.id.location_details)
        val priceView = findViewById<TextView>(R.id.price_details)
        val descrView = findViewById<TextView>(R.id.descr_details)
        val orgView = findViewById<TextView>(R.id.org_details)

        Glide.with(this)
            .load(event.image)
            .into(imageView)
        titleView.text = event.title
        dateView.text = event.schedule[0].date
        locationView.text = event.schedule[0].location
        priceView.text = String.format("$%02d.00", event.ticketTypes[0].price)
        descrView.text = event.description
        orgView.text = event.organizer

        val btn = findViewById<ImageView>(R.id.backBtn)
        val origin = intent.getStringExtra("origin")
        val query = intent.getStringExtra("query")

        btn.setOnClickListener {
            when (origin) {
                "search" -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    intent.putExtra("query", query)
                    startActivity(intent)
                }
                else -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
            finish()
        }

    }
}