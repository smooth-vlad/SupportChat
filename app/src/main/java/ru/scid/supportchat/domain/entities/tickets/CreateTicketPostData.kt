package ru.scid.supportchat.domain.entities.tickets

import com.google.gson.annotations.SerializedName

data class CreateTicketPostData(

    @SerializedName("request")
    val ticket: TicketPostData

)