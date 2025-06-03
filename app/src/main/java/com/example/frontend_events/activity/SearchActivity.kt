package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_events.ApiInterface
import com.example.frontend_events.R
import com.example.frontend_events.adapters.RecomAdapter
import com.example.frontend_events.adapters.SearchAdapter
import com.example.frontend_events.models.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable

class SearchActivity : AppCompatActivity() {

    private lateinit var allEvents: List<Event>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private lateinit var searchEditText: EditText
    private var query: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerView = findViewById(R.id.search_list)
        searchEditText = findViewById(R.id.search_text)


        query = intent.getStringExtra("query") ?: ""
        searchEditText.setText(query)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = SearchAdapter(emptyList()) { selectedEvent ->
            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("event", selectedEvent as Serializable)
            intent.putExtra("origin", "search")
            intent.putExtra("query", query)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        getMyData { events ->
            if (events.isNotEmpty()) {
                val filtered = events.filter {
                    it.title.contains(query, ignoreCase = true) ||
                            it.schedule[0].location.contains(query, ignoreCase = true) ||
                            it.schedule[0].date.contains(query, ignoreCase = true)
                }

                if (filtered.isEmpty()) {
                    val noResultsText = findViewById<TextView>(R.id.noResultsText)
                    noResultsText.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
                }
                adapter.updateData(filtered)

                fun filterEvents(query: String) {
                    val filtered = events.filter {
                        it.title.contains(query, ignoreCase = true) ||
                                it.schedule[0].location.contains(query, ignoreCase = true) ||
                                it.schedule[0].date.contains(query, ignoreCase = true)
                    }

                    adapter.updateData(filtered)

                    val noResultsText = findViewById<TextView>(R.id.noResultsText)
                    noResultsText.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
                }

                searchEditText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        query = s.toString()
                        filterEvents(query)
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })

            } else {
                Log.d("MainActivity", "No events received")
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
}