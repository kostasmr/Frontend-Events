package com.example.frontend_events

import com.example.frontend_events.models.Event
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET(value = "events")
    fun getData(): Call<List<Event>>
}