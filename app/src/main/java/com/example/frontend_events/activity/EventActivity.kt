package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.frontend_events.R
import com.example.frontend_events.models.Event
import com.example.frontend_events.ApiInterface
import okhttp3.ResponseBody
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import android.net.Uri
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient

import com.example.frontend_events.models.TicketOrder //type of ticket order


class EventActivity : AppCompatActivity() {

    private lateinit var map: MapView
    // Replace with your coordinates
    val lat = 37.9838
    val lng = 23.7275

    private lateinit var heartBtn: ImageView
    private var isFavorited = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        //dates
        val event = intent.getSerializableExtra("event") as Event
        isFavorited = event.favorited
        var ticketDetails = intent.getSerializableExtra("ticketOrderInfo") as? TicketOrder //details od ticket order


        val imageView = findViewById<ImageView>(R.id.imageView)
        val titleView = findViewById<TextView>(R.id.title_details)
        val dateView = findViewById<TextView>(R.id.date_details)
        val locationView = findViewById<TextView>(R.id.location_details)
        val priceView = findViewById<TextView>(R.id.price_details)
        val descrView = findViewById<TextView>(R.id.descr_details)
        val orgView = findViewById<TextView>(R.id.org_details)
        heartBtn = findViewById<ImageView>(R.id.heart)

        Glide.with(this)
            .load(event.image)
            .into(imageView)
        titleView.text = event.title
        dateView.text = event.schedule[0].date
        locationView.text = event.schedule[0].location
        priceView.text = String.format("$%02d.00", event.ticketTypes[0].price)
        descrView.text = event.description
        orgView.text = event.organizer

        updateHeartIcon(isFavorited)

        // Handle favorite toggle
        heartBtn.setOnClickListener {
            val api = getApiService()
            val body = mapOf("eventId" to event.eventId)

            if (!isFavorited) {
                api.addFavorite(body).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            isFavorited = true
                            event.favorited = true
                            updateHeartIcon(true)
                        } else if (response.code() == 409) {
                            Toast.makeText(this@EventActivity, "Already in favorites", Toast.LENGTH_SHORT).show()
                            isFavorited = true
                            event.favorited = true
                            updateHeartIcon(true)
                        } else {
                            Toast.makeText(this@EventActivity, "Failed to favorite", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e("EventActivity", "Failed to add favorite: ${t.message}")
                    }
                })
            } else {
                api.removeFavorite(body).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            isFavorited = false
                            event.favorited = false
                            updateHeartIcon(false)
                        } else {
                            Toast.makeText(this@EventActivity, "Failed to unfavorite", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e("EventActivity", "Failed to remove favorite: ${t.message}")
                    }
                })
            }
        }

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

        Configuration.getInstance()
            .load(applicationContext, getSharedPreferences("osmdroid", MODE_PRIVATE))
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
            val gmmIntentUri =
                Uri.parse("geo:${lat},${lng}?q=${lat},${lng}(${event.schedule[0].location})")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps") // Optional: to prefer Google Maps app

            // Check if an app exists to handle the intent
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                // Fallback: open in browser or show message
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=${lat},${lng}")
                )
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

    private fun updateHeartIcon(isFavorited: Boolean) {
        val icon = if (isFavorited) R.drawable.ic_heart_pink else R.drawable.ic_white_heart
        heartBtn.setImageResource(icon)
    }

    private fun getApiService(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}