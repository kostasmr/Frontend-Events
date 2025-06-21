package com.example.frontend_events.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
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

        // menu bar
        val homeBtn = findViewById<ImageView>(R.id.homeBtn)
        val ticketsBtn = findViewById<ImageView>(R.id.ticketsBtn)
        val profileBtn = findViewById<ImageView>(R.id.profileBtn)

        homeBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // optional: κλείνει το τρέχον activity
        }

        profileBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        ticketsBtn.setOnClickListener {
            val intent = Intent(this, TicketListActivity::class.java)
            startActivity(intent)
            finish()
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


