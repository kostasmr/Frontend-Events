package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.frontend_events.R
import com.example.frontend_events.models.Event
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import android.net.Uri
import com.example.frontend_events.models.TicketOrder //type of ticket order


class EventActivity : AppCompatActivity() {

    private lateinit var map: MapView
    // Replace with your coordinates
    val lat = 37.9838
    val lng = 23.7275

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
    //dates
        val event = intent.getSerializableExtra("event") as Event
        var ticketDetails = intent.getSerializableExtra("ticketOrderInfo") as? TicketOrder //details od ticket order


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

        //ticketorder details

        ticketDetails = TicketOrder(
            title = event.title,
            description = event.description,
            image = event.image,
            location = event.schedule[0].location,
            price = String.format("$%02d.00", event.ticketTypes[0].price),
            paymentMethod = "",
            purchaserName = "",
            numberOfTickets = 1,
            discountCategory = ""
        )


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

        val buyBtn = findViewById<ConstraintLayout>(R.id.buyBtn)
        buyBtn.setOnClickListener {
            val intent = Intent(this@EventActivity, OrderDetailActivity::class.java)
            intent.putExtra("event", event)
            intent.putExtra("ticketOrderInfo", ticketDetails)
            startActivity(intent)
        }

        //map content

        Configuration.getInstance().load(applicationContext, getSharedPreferences("osmdroid", MODE_PRIVATE))
        map = findViewById(R.id.map)
        map.setMultiTouchControls(true) // enable zoom controls

        val startPoint = GeoPoint(lat, lng)
        map.controller.setZoom(16.0)
        map.controller.setCenter(startPoint)

        // Add a marker
        val marker = Marker(map)
        marker.position = startPoint
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = "Athens"
        map.overlays.add(marker)

        val openMapsButton = findViewById<TextView>(R.id.locationBtn)
        openMapsButton.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:${lat},${lng}?q=${lat},${lng}(${event.schedule[0].location})")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps") // Optional: to prefer Google Maps app

            // Check if an app exists to handle the intent
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                // Fallback: open in browser or show message
                val browserIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=${lat},${lng}"))
                startActivity(browserIntent)
            }
        }

        // chat

        val chatBtn = findViewById<ImageView>(R.id.chatBtn)
        chatBtn.setOnClickListener {
            val intent = Intent(this@EventActivity, ChatActivity::class.java)
            intent.putExtra("event", event)
            startActivity(intent)
        }

    }

}