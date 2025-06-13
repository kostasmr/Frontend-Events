package com.example.frontend_events

import com.example.frontend_events.models.Event
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @GET(value = "events")
    fun getData(): Call<List<Event>>

    @POST(value = "recoms")
    fun getRecomEvents(@Body body: List<String>): Call<List<Event>>
}