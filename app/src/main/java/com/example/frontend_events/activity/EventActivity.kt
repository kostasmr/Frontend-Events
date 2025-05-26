package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend_events.R
import com.example.frontend_events.models.Event

class EventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        val event = intent.getSerializableExtra("event") as Event

        println(event)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val titleView = findViewById<TextView>(R.id.title_details)
        val dateView = findViewById<TextView>(R.id.date_details)
        val locationView = findViewById<TextView>(R.id.location_details)
        val priceView = findViewById<TextView>(R.id.price_details)
        val descrView = findViewById<TextView>(R.id.descr_details)
        val orgView = findViewById<TextView>(R.id.org_details)

        imageView.setImageResource(event.imageId)
        titleView.text = event.title
        dateView.text = event.date
        locationView.text = event.location
        priceView.text = event.price
        descrView.text = event.description
        orgView.text = event.organizers

        val btn = findViewById<ImageView>(R.id.backBtn)

        btn.setOnClickListener {
            val intent = Intent(this@EventActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}