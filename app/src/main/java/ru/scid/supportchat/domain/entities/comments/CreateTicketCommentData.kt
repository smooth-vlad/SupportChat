package ru.scid.supportchat.domain.entities.comments

import com.google.gson.annotations.SerializedName
import ru.scid.supportchat.domain.entities.tickets.CommentPostData

data class CreateTicketCommentData(
    @SerializedName("comment")
    val comment: CommentPostData
)