package com.example.frontend_events.activity

import TicketAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_events.R
import com.example.frontend_events.adapters.TicketListTrack  // ← Πρέπει να υπάρχει αυτό
import com.example.frontend_events.models.TicketOrder         // ← Ελέγχεις ότι υπάρχει και αυτό

class TicketListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_list)
        val goHomeButton = findViewById<Button>(R.id.goHomeButton)

        // Παίρνουμε τη λίστα εισιτηρίων (mock ή πραγματική)
        val ticketList: List<TicketOrder> = TicketListTrack.getAllTickets()

        // Σύνδεση RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.ticketRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TicketAdapter(ticketList)

        goHomeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // optional: κλείνει το τρέχον activity
        }


    }
}
