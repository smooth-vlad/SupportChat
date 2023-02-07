package ru.scid.supportchat.domain.entities.comments

import com.google.gson.annotations.SerializedName

data class CreateTicketCommentPutData(
    @SerializedName("request")
    val ticket: CreateTicketCommentData
)