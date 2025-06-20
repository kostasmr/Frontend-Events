package com.example.frontend_events.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_events.ApiInterface
import com.example.frontend_events.R
import com.example.frontend_events.adapters.FavoritesAdapter
import com.example.frontend_events.models.Event
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class FavoritesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoritesAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        recyclerView = findViewById(R.id.favoritesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = FavoritesAdapter(emptyList()) { selectedEvent ->
            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("event", selectedEvent)
            intent.putExtra("origin", "favorites")
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        fetchFavorites()

        // Find each navigation button by ID
        val navHome = findViewById<LinearLayout>(R.id.nav_home)
        val navTicket = findViewById<LinearLayout>(R.id.nav_ticket)
        val navFavorites = findViewById<LinearLayout>(R.id.nav_favorites)
        val navProfile = findViewById<LinearLayout>(R.id.nav_profile)
        val scrollView = findViewById<ScrollView>(R.id.scrollView2)


        // Set click listeners
        navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        navTicket.setOnClickListener {
            // TODO: Replace with your ticket activity intent if exists
            // Example:
            // val intent = Intent(this, TicketActivity::class.java)
            // startActivity(intent)
        }
        // Navigate to Favorites view
        navFavorites.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
        // Navigate to Profile view
        navProfile.setOnClickListener {
//            val intent = Intent(this, ProfileActivity::class.java)
//            startActivity(intent)
        }
    }



    private fun fetchFavorites() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        api.getFavorites().enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                val favorites = response.body() ?: emptyList()
                adapter.updateData(favorites)
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                Log.e("FavoritesActivity", "Failed to load favorites: ${t.message}")
            }
        })
    }
}


