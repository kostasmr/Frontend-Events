package com.example.frontend_events.models

import java.io.Serializable

data class TicketType(
    val availableTickets: Int,
    val price: Int,
    val type: String
) : Serializable