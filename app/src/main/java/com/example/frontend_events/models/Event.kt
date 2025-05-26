package com.example.frontend_events.models
import java.io.Serializable

class Event(
    val imageId: Int,
    val title: String,
    val date: String,
    val location: String,
    val price: String,
    val description: String,
    val organizers: String
    ) : Serializable