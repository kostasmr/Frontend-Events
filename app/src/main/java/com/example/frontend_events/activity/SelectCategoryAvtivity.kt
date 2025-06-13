package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.frontend_events.R
import kotlin.collections.remove
import kotlin.text.contains

class SelectCategoryAvtivity : AppCompatActivity() {

    private lateinit var next: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_category)

        var isSelected = false
        val selectedCategories = mutableListOf<String>()

        fun catOnClick(category: String, layout: LinearLayout){
            if (!selectedCategories.contains(category)) {
                selectedCategories.add(category)
                layout.setBackgroundResource(R.drawable.selected_category_shape)
            } else {
                selectedCategories.remove(category)
                layout.setBackgroundResource(R.drawable.main_shape)
            }
        }

        val cat1 = findViewById<LinearLayout>(R.id.concert_layout)
        val cat2 = findViewById<LinearLayout>(R.id.theater_layout)
        val cat3 = findViewById<LinearLayout>(R.id.sports_layout)
        val cat4 = findViewById<LinearLayout>(R.id.festival_layout)
        val cat5 = findViewById<LinearLayout>(R.id.conference_layout)
        val cat6 = findViewById<LinearLayout>(R.id.workshop_layout)
        val cat7 = findViewById<LinearLayout>(R.id.comedy_layout)
        val cat8 = findViewById<LinearLayout>(R.id.movie_layout)
        val cat9 = findViewById<LinearLayout>(R.id.exhibition_layout)

        cat1.setOnClickListener {
            catOnClick("concert", cat1)
        }
        cat2.setOnClickListener {
            catOnClick("theater", cat2)
        }
        cat3.setOnClickListener {
            catOnClick("sports", cat3)
        }
        cat4.setOnClickListener {
            catOnClick("festival", cat4)
        }
        cat5.setOnClickListener {
            catOnClick("conference", cat5)
        }
        cat6.setOnClickListener {
            catOnClick("workshop", cat6)
        }
        cat7.setOnClickListener {
            catOnClick("comedy", cat7)
        }
        cat8.setOnClickListener {
            catOnClick("movie", cat8)
        }
        cat9.setOnClickListener {
            catOnClick("exhibition", cat9)
        }

        next = findViewById(R.id.startBtn)

        next.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putStringArrayListExtra("selectedCategories", ArrayList(selectedCategories))
            startActivity(intent)
            finish()
        }
    }
}