package ru.scid.supportchat.domain.entities.tickets

import com.google.gson.annotations.SerializedName

data class CommentPostData(

    @SerializedName("body")
    val body: String

)