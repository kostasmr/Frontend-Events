package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.frontend_events.R
import com.example.frontend_events.adapters.TicketListTrack
import com.example.frontend_events.models.TicketOrder

class PaypalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paypal_layout)

        var ticketDetails = intent.getSerializableExtra("ticketOrderInfo") as? TicketOrder


        val emailField = findViewById<EditText>(R.id.emailField)
        val nameField = findViewById<EditText>(R.id.nameField)
        val nextButton = findViewById<AppCompatButton>(R.id.nextButton)

        nextButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val name = nameField.text.toString().trim()

            if (email.isBlank() || name.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Proceeding with PayPal", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ValidPayActivity::class.java)
                if (ticketDetails != null) {
                    ticketDetails.purchaserName = nameField.text.toString().trim()
                    TicketListTrack.addTicket(ticketDetails)
                }
                startActivity(intent)
            }
        }
    }
}

