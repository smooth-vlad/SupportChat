package ru.scid.supportchat.domain.entities.tickets

import com.google.gson.annotations.SerializedName

data class TicketPostData(

    @SerializedName("subject")
    val subject: String,
    @SerializedName("comment")
    val comment: CommentPostData,

    )