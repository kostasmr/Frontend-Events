package com.example.frontend_events

import com.example.frontend_events.models.Event
import com.example.frontend_events.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiInterface {

    @GET(value = "events")
    fun getData(): Call<List<Event>>

    @POST(value = "recoms")
    fun getRecomEvents(@Body body: List<String>): Call<List<Event>>

    @POST(value = "users")
    fun createUser(@Body body: User): Call<User>

    @POST(value = "users/login")
    fun loginUser(@Body body: Map<String, String>): Call<User>

    @PUT(value = "users/{id}")
    fun updateUser(@Path("id") id: String?,@Body body: Map<String, String>): Call<User>
}