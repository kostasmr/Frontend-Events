package com.example.frontend_events.adapters

import com.example.frontend_events.models.TicketOrder

object TicketListTrack {

    // Εσωτερική λίστα TicketOrders
    private val ticketOrders: MutableList<TicketOrder> = mutableListOf()

    // Προσθήκη TicketOrder
    fun addTicket(ticket: TicketOrder) {
        ticketOrders.add(ticket)
    }

    // Επιστροφή όλων των TicketOrders
    fun getAllTickets(): List<TicketOrder> {
        return ticketOrders
    }
}
