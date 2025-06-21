package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend_events.R

class ValidPayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.validpay_layout)

        val viewTicketButton = findViewById<Button>(R.id.btn_view_ticket)
        val goHomeButton = findViewById<Button>(R.id.btn_go_home)
        val btnViewTicket = findViewById<Button>(R.id.btn_view_ticket)

        viewTicketButton.setOnClickListener {
            // Άνοιξε E-Ticket (π.χ. άλλη Activity)
            //val intent = Intent(this, TicketActivity::class.java)
            //startActivity(intent)
        }

        goHomeButton.setOnClickListener {
            // Πήγαινε στην αρχική οθόνη
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        btnViewTicket.setOnClickListener {
            val intent = Intent(this, TicketListActivity::class.java)
            startActivity(intent)
        }
    }
}
