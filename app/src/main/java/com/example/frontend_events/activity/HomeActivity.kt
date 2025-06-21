package com.example.frontend_events.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
//<<<<<<< HEAD
import android.widget.ImageButton
//=======
//>>>>>>> 956b5996811a3644f0f3cd5b95f958b20cb8050f
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_events.adapters.Adapter
import com.example.frontend_events.ApiInterface
import com.example.frontend_events.AppState
import com.example.frontend_events.adapters.RecomAdapter
import com.example.frontend_events.R
import com.example.frontend_events.models.Event
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable
import android.widget.ImageView


const val BASE_URL = "https://eventapp-backend-c8xe.onrender.com/api/"
//const val BASE_URL = "http://10.0.2.2:8080/api/"

class HomeActivity : AppCompatActivity() {

    private lateinit var loadingSpinner: ProgressBar
    private lateinit var retryButton: Button
    private lateinit var recomCategories: ArrayList<String>


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        loadingSpinner = findViewById(R.id.loadingSpinner)
        retryButton = findViewById(R.id.retryButton)

        // Retry logic
        retryButton.setOnClickListener {
            showLoading()
            loadAllEvents()
        }


        //menu bar
        val profile = findViewById<ImageView>(R.id.profileBtn)

        profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


        val popularView = findViewById<RecyclerView>(R.id.popular_list)
        val recomView = findViewById<RecyclerView>(R.id.recom_list)
        popularView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recomView.layoutManager = LinearLayoutManager(this)


        val adapter = Adapter(emptyList()) { selectedEvent ->
            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("event", selectedEvent as Serializable)
            intent.putExtra("origin", "home")
            startActivity(intent)
        }
        val recomAdapter = RecomAdapter(emptyList()) { selectedEvent ->
            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("event", selectedEvent as Serializable)
            startActivity(intent)
        }

        popularView.adapter = adapter
        recomView.adapter = recomAdapter

        showLoading()
        loadAllEvents()


        // Search Input
        val searchEditText = findViewById<EditText>(R.id.search_input)

        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                val query = searchEditText.text.toString()
                val intent = Intent(this, SearchActivity::class.java)
                intent.putExtra("query", query)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                searchEditText.setText("")
                true
            } else {
                false
            }
        }

        //Categories scroll View
        val cat1 = findViewById<LinearLayout>(R.id.concert_layout)
        val cat2 = findViewById<LinearLayout>(R.id.theater_layout)
        val cat3 = findViewById<LinearLayout>(R.id.sports_layout)
        val cat4 = findViewById<LinearLayout>(R.id.festival_layout)
        val cat5 = findViewById<LinearLayout>(R.id.conference_layout)
        val cat6 = findViewById<LinearLayout>(R.id.workshop_layout)
        val cat7 = findViewById<LinearLayout>(R.id.comedy_layout)
        val cat8 = findViewById<LinearLayout>(R.id.movie_layout)
        val cat9 = findViewById<LinearLayout>(R.id.exhibition_layout)

        fun catOnClick(query: String){
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("query", query)
            searchEditText.setText(query)
            startActivity(intent)
        }
        cat1.setOnClickListener {
            catOnClick("concert")
        }
        cat2.setOnClickListener {
            catOnClick("theater")
        }
        cat3.setOnClickListener {
            catOnClick("sports")
        }
        cat4.setOnClickListener {
            catOnClick("festival")
        }
        cat5.setOnClickListener {
            catOnClick("conference")
        }
        cat6.setOnClickListener {
            catOnClick("workshop")
        }
        cat7.setOnClickListener {
            catOnClick("comedy")
        }
        cat8.setOnClickListener {
            catOnClick("movie")
        }
        cat9.setOnClickListener {
            catOnClick("exhibition")
        }

        // See all on click
        val see_all = findViewById<TextView>(R.id.see_all)
        val see_all2 = findViewById<TextView>(R.id.see_all2)

        see_all.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        see_all2.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        val ticketButton = findViewById<ImageView>(R.id.ticketsBtn)
        ticketButton.setOnClickListener {
            val intent = Intent(this, TicketListActivity::class.java)
            startActivity(intent)
        }
        // Find each navigation button by ID
        val navHome = findViewById<LinearLayout>(R.id.nav_home)
        val navTicket = findViewById<LinearLayout>(R.id.nav_ticket)
        val navFavorites = findViewById<LinearLayout>(R.id.nav_favorites)
        val navProfile = findViewById<LinearLayout>(R.id.nav_profile)
        val scrollView = findViewById<ScrollView>(R.id.scrollView2)


        // Set click listeners
        navHome.setOnClickListener {
            scrollView.smoothScrollTo(0, 0)
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

    // Load all events
    private fun loadAllEvents() {
        getMyData { events ->
            val popularView = findViewById<RecyclerView>(R.id.popular_list)
            val adapter = popularView.adapter as Adapter

            if (events.isNotEmpty()) {
                val popular = listOf(events[2], events[4], events[5])
                adapter.updateData(popular)
            } else {
                Log.d("HomeActivity", "No events received")
            }
        }

        getMyRecomEvents { recomEvents ->
            val recomView = findViewById<RecyclerView>(R.id.recom_list)
            val recomAdapter = recomView.adapter as RecomAdapter

            if (recomEvents.isNotEmpty()) {
                recomAdapter.updateData(recomEvents)
                hideLoading()
            } else {
                Log.d("HomeActivity", "No recommendation events received")
                showRetry()
            }
        }
    }

    fun getMyData(onResult: (List<Event>) -> Unit){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()


        retrofitData.enqueue(object: Callback<List<Event>>{
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                val events = response.body()
                if (!events.isNullOrEmpty()) {
                    onResult(events)
                } else {
                    onResult(emptyList())
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                Log.d("HomeActivity", "onFailure: ${t.message}")
                onResult(emptyList())
            }
        })
    }

    fun getMyRecomEvents(onResult: (List<Event>) -> Unit){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getRecomEvents(AppState.recomCategories)


        retrofitData.enqueue(object: Callback<List<Event>>{
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                val events = response.body()
                if (!events.isNullOrEmpty()) {
                    onResult(events)
                } else {
                    onResult(emptyList())
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                Log.d("HomeActivity", "onFailure: ${t.message}")
                onResult(emptyList())
            }
        })
    }

    private fun showLoading() {
        loadingSpinner.visibility = View.VISIBLE
        retryButton.visibility = View.GONE
    }

    private fun hideLoading() {
        loadingSpinner.visibility = View.GONE
        retryButton.visibility = View.GONE
    }

    private fun showRetry() {
        loadingSpinner.visibility = View.GONE
        retryButton.visibility = View.VISIBLE
    }

}


