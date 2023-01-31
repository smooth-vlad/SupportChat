package ru.scid.supportchat.domain.entities.tickets

import com.google.gson.annotations.SerializedName

data class TicketData(
    @SerializedName("id")
    val id: Long,
    @SerializedName("status")
    val status: String,
    @SerializedName("subject")
    val subject: String,
    @SerializedName("description")
    val description: String,
)