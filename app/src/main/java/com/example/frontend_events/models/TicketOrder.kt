package com.example.frontend_events.models

import java.io.Serializable
import java.util.UUID

data class TicketOrder(
    var id: String = UUID.randomUUID().toString(), // Αυτόματο μοναδικό ID
    var title: String = "",
    var description: String = "",
    var image: String = "",
    var location: String = "",
    var price: String = "",
    var paymentMethod: String = "",
    var purchaserName: String = "",
    var numberOfTickets: Int = 0,
    var discountCategory: String = "",
    var qrCode: String = UUID.randomUUID().toString()
) : Serializable
