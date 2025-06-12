package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.frontend_events.R

class SelectCategoryAvtivity : AppCompatActivity() {

    private lateinit var next: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_category)

        var isSelected = false
        val selected_categories = mutableListOf<String>()


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
            if (!selected_categories.contains("Concert")) {
                selected_categories.add("Concert")
                cat1.setBackgroundResource(R.drawable.selected_category_shape)
            } else {
                selected_categories.remove("Concert")
                cat1.setBackgroundResource(R.drawable.main_shape)
            }
        }

        cat2.setOnClickListener {
            if (!selected_categories.contains("Theater")) {
                selected_categories.add("Theater")
                cat2.setBackgroundResource(R.drawable.selected_category_shape)
            } else {
                selected_categories.remove("Theater")
                cat2.setBackgroundResource(R.drawable.main_shape)
            }
        }
        cat3.setOnClickListener {
            if (!selected_categories.contains("Sports")) {
                selected_categories.add("Sports")
                cat3.setBackgroundResource(R.drawable.selected_category_shape)
            } else {
                selected_categories.remove("Sports")
                cat3.setBackgroundResource(R.drawable.main_shape)
            }
        }
        cat4.setOnClickListener {
            if (!selected_categories.contains("Festival")) {
                selected_categories.add("Festival")
                cat4.setBackgroundResource(R.drawable.selected_category_shape)
            } else {
                selected_categories.remove("Festival")
                cat4.setBackgroundResource(R.drawable.main_shape)
            }
        }
        cat5.setOnClickListener {
            if (!selected_categories.contains("Conference")) {
                selected_categories.add("Conference")
                cat5.setBackgroundResource(R.drawable.selected_category_shape)
            } else {
                selected_categories.remove("Conference")
                cat5.setBackgroundResource(R.drawable.main_shape)
            }
        }
        cat6.setOnClickListener {
            if (!selected_categories.contains("Workshop")) {
                selected_categories.add("Workshop")
                cat6.setBackgroundResource(R.drawable.selected_category_shape)
            } else {
                selected_categories.remove("Workshop")
                cat6.setBackgroundResource(R.drawable.main_shape)
            }
        }
        cat7.setOnClickListener {
            if (!selected_categories.contains("Comedy")) {
                selected_categories.add("Comedy")
                cat7.setBackgroundResource(R.drawable.selected_category_shape)
            } else {
                selected_categories.remove("Comedy")
                cat7.setBackgroundResource(R.drawable.main_shape)
            }
        }
        cat8.setOnClickListener {
            if (!selected_categories.contains("Movie")) {
                selected_categories.add("Movie")
                cat8.setBackgroundResource(R.drawable.selected_category_shape)
            } else {
                selected_categories.remove("Movie")
                cat8.setBackgroundResource(R.drawable.main_shape)
            }
        }
        cat9.setOnClickListener {
            if (!selected_categories.contains("Exhibition")) {
                selected_categories.add("Exhibition")
                cat9.setBackgroundResource(R.drawable.selected_category_shape)
            } else {
                selected_categories.remove("Exhibition")
                cat9.setBackgroundResource(R.drawable.main_shape)
            }
        }
        next = findViewById(R.id.startBtn)

        next.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}