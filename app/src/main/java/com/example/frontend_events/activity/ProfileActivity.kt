package com.example.frontend_events.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend_events.R

class ProfileActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Find each navigation button by ID
        val navHome = findViewById<LinearLayout>(R.id.nav_home)
        val navTicket = findViewById<LinearLayout>(R.id.nav_ticket)
        val navFavorites = findViewById<LinearLayout>(R.id.nav_favorites)
        val navProfile = findViewById<LinearLayout>(R.id.nav_profile)


        // Set click listeners
        navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        navTicket.setOnClickListener {
            val intent = Intent(this, TicketActivity::class.java)
            startActivity(intent)
        }
        // Navigate to Favorites view
        navFavorites.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
        // Navigate to Profile view
        navProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
