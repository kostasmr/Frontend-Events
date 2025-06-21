package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.frontend_events.R

class ValidPayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.validpay_layout)

        val viewTicketButton = findViewById<AppCompatButton>(R.id.btn_view_ticket)
        val goHomeButton = findViewById<AppCompatButton>(R.id.btn_go_home)

        goHomeButton.setOnClickListener {
            // Πήγαινε στην αρχική οθόνη
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        viewTicketButton.setOnClickListener {
            val intent = Intent(this, TicketListActivity::class.java)
            startActivity(intent)
        }
    }
}
