package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend_events.R
import com.example.frontend_events.adapters.TicketListTrack
import com.example.frontend_events.models.TicketOrder

class AddCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creditcard_form_layout2)

        var ticketDetails = intent.getSerializableExtra("ticketOrderInfo") as? TicketOrder


        val cardNumberField = findViewById<EditText>(R.id.edit_card_number)
        val expiryDateField = findViewById<EditText>(R.id.edit_expiry_date)
        val cvcField = findViewById<EditText>(R.id.edit_cvc)
        val cardholderField = findViewById<EditText>(R.id.edit_cardholder)
        val buyTicketButton = findViewById<Button>(R.id.btn_buy_ticket)

        buyTicketButton.setOnClickListener {
            val cardNumber = cardNumberField.text.toString().trim()
            val expiryDate = expiryDateField.text.toString().trim()
            val cvc = cvcField.text.toString().trim()
            val cardholder = cardholderField.text.toString().trim()

            if (cardNumber.isBlank() || expiryDate.isBlank() || cvc.isBlank() || cardholder.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (cardNumber.length != 16 || !cardNumber.all { it.isDigit() }) {
                Toast.makeText(this, "Invalid card number", Toast.LENGTH_SHORT).show()
            } else if (cvc.length != 3 || !cvc.all { it.isDigit() }) {
                Toast.makeText(this, "Invalid CVC", Toast.LENGTH_SHORT).show()
            } else if (!expiryDate.matches(Regex("\\d{2}/\\d{2}"))) {
                Toast.makeText(this, "Invalid expiry date format (MM/YY)", Toast.LENGTH_SHORT).show()
            } else {
                val parts = expiryDate.split("/")
                val month = parts[0].toIntOrNull()
                val year = parts[1].toIntOrNull()

                if (month == null || year == null || month !in 1..12 || year < 24) {
                    Toast.makeText(this, "Invalid expiry date. Use MM/YY (e.g. 06/25)", Toast.LENGTH_SHORT).show()
                } else {
                    // Όλα έγκυρα
                    Toast.makeText(this, "Proceeding with card payment", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ValidPayActivity::class.java)
                    if (ticketDetails != null) {
                        ticketDetails.purchaserName = cardholderField.text.toString().trim()
                        TicketListTrack.addTicket(ticketDetails)
                    }
                    intent.putExtra("ticketOrderInfo", ticketDetails)

                    startActivity(intent)
                }
            }
        }
    }
}
