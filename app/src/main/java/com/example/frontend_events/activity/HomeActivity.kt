package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_events.adapters.Adapter
import com.example.frontend_events.ApiInterface
import com.example.frontend_events.adapters.RecomAdapter
import com.example.frontend_events.R
import com.example.frontend_events.models.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable

//const val BASE_URL = "https://eventapp-backend-c8xe.onrender.com/api/"
const val BASE_URL = "http://10.0.2.2:8080/api/"

class HomeActivity : AppCompatActivity() {

    private lateinit var recomCategories: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recomCategories = intent.getStringArrayListExtra("selectedCategories") ?: arrayListOf()

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

        // Load all events
        getMyData { events ->
            if (events.isNotEmpty()) {
                var popular = listOf(events[0],events[1])
                adapter.updateData(popular)
//                var recom = listOf(events[2],events[3],events[4],events[5])
//                recomAdapter.updateData(recom)
            } else {
                Log.d("HomeActivity", "No events received")
            }
        }

        // Load recommendation events
        getMyRecomEvents { recomEvents ->
            if (recomEvents.isNotEmpty()) {
                var recom = recomEvents
                recomAdapter.updateData(recom)
            } else {
                Log.d("HomeActivity", "No recommendation events received")
            }
        }

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

        val retrofitData = retrofitBuilder.getRecomEvents(recomCategories)


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


