package ru.scid.supportchat.domain.entities.tickets

import com.google.gson.annotations.SerializedName

data class CreateTicketData(
    @SerializedName("ticket")
    val ticket: TicketData
)