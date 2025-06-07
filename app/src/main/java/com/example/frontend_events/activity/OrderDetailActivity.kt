package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.frontend_events.R
import com.example.frontend_events.models.Event

class OrderDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_layout)


        val event = intent.getSerializableExtra("event") as Event

        val imageView = findViewById<ImageView>(R.id.imageEventOrder)
        val titleView = findViewById<TextView>(R.id.titleEventOrder)
        val dateView = findViewById<TextView>(R.id.dateEventOrder)
        val locationView = findViewById<TextView>(R.id.locationEventOrder)
        val priceView = findViewById<TextView>(R.id.ticketPriceValue)
        val finalvalue = findViewById<TextView>(R.id.priceValue)
        val totalvalue = findViewById<TextView>(R.id.totalValue)
        val feevalue = findViewById<TextView>(R.id.feesValue)

        Glide.with(this)
            .load(event.image)
            .into(imageView)
        titleView.text = event.title
        dateView.text = event.schedule[0].date
        locationView.text = event.schedule[0].location
        priceView.text = String.format("$%02d.00", event.ticketTypes[0].price)

        val spinner = findViewById<Spinner>(R.id.quantitySpinner)

        // Δημιουργούμε λίστα 1 έως 10
        val quantities = (1..10).map { it.toString() }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, quantities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Θέτουμε εξ ορισμού την τιμή στο "1" (δηλαδή θέση 0)
        spinner.setSelection(0)


        // Συνδέσεις στοιχείων
        val placeOrderButton = findViewById<Button>(R.id.placeOrderButton)
        val radioCard = findViewById<RadioButton>(R.id.radioCard)
        val radioPaypal = findViewById<RadioButton>(R.id.radioPaypal)

        val discountSpinner = findViewById<Spinner>(R.id.discountSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.discount_categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            discountSpinner.adapter = adapter
        }

        // Θέτουμε εξ ορισμού την τιμή στο "1" (δηλαδή θέση 0)
        discountSpinner.setSelection(0)


        // Αρχικά ενεργό το πρώτο
        radioCard.isChecked = true

        spinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedQuantity = parent.getItemAtPosition(position).toString().toInt()
                // Αν θέλεις, ενημέρωσε το συνολικό ποσό
                val ticketPrice = event.ticketTypes[0].price
                val feeText = feevalue.text.toString()            // "$03.00"
                val numericPart = feeText.replace("$", "").replace(".00", "") // "03"
                val feeAsInt = numericPart.toInt()                // 3
                val total = ticketPrice * selectedQuantity + feeAsInt
                totalvalue.text = String.format("$%02d.00", total)
                finalvalue.text = String.format("$%02d.00", total)

            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {
                // Δεν επιλέχθηκε τίποτα — συνήθως δεν χρειάζεται ενέργεια εδώ
            }
        }

        val backBtn = findViewById<ImageButton>(R.id.backButton)

        backBtn.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@OrderDetailActivity, EventActivity::class.java)
            startActivity(intent)
            finish()
        }


        // διαχειριση στοιχειων

        discountSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val discountCategory = parent.getItemAtPosition(position).toString()
                val selectedQuantity = spinner.selectedItem.toString().toInt()
                val ticketPrice = event.ticketTypes[0].price
                val fees = findViewById<TextView>(R.id.feesValue).text.toString()
                    .replace("$", "").replace(".00", "").toInt()

                // Υπολογισμός αρχικής τιμής (χωρίς έκπτωση)
                val subtotal = ticketPrice * selectedQuantity

                // Υπολογισμός έκπτωσης
                val discountPercent = when (discountCategory) {
                    "Unemployed" -> 0.10
                    "Student", "Special Need Person" -> 0.20
                    else -> 0.0
                }

                val discountedTotal = (subtotal * (1 - discountPercent)).toInt() + fees

                // Ενημέρωση TextView με τελική τιμή
                totalvalue.text = String.format("$%02d.00", discountedTotal)
                finalvalue.text = String.format("$%02d.00", discountedTotal)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Καμία ενέργεια
            }
        }



        radioCard.setOnClickListener {
            radioCard.isChecked = true
            radioPaypal.isChecked = false
        }

        radioPaypal.setOnClickListener {
            radioCard.isChecked = false
            radioPaypal.isChecked = true
        }

        // Παράδειγμα ενεργειών στο "Place Order"
        placeOrderButton.setOnClickListener {
            val intent = Intent(this@OrderDetailActivity, SearchActivity::class.java)
            startActivity(intent)
            finish() // Προαιρετικά: κλείνει την τρέχουσα Activity
        }
    }
}


