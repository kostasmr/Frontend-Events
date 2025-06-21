package com.example.frontend_events.models

data class User(
    val _id: String? = null,
    val name: String,
    val email: String,
    val password: String,
    val userImg: String? = null,
    val about: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val __v: Int? = null
)
