package ru.scid.supportchat.domain.entities.tickets

import com.google.gson.annotations.SerializedName

data class ListTicketsData(
    @SerializedName("requests")
    val tickets: List<TicketData>
)