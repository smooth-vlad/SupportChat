package ru.scid.supportchat.domain.entities.comments

import com.google.gson.annotations.SerializedName

data class ListTicketCommentsData(
    @SerializedName("comments")
    val tickets: List<TicketCommentData>
)