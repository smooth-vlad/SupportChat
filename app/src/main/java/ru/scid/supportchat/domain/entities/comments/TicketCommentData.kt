package ru.scid.supportchat.domain.entities.comments

import com.google.gson.annotations.SerializedName

data class TicketCommentData(
    @SerializedName("id")
    val id: Long,
    @SerializedName("author_id")
    val authorId: Long,
    @SerializedName("body")
    val body: String,
    @SerializedName("created_at")
    val createdAt: String,
)