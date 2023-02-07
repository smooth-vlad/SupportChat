package ru.scid.supportchat.domain.entities.auth

import com.google.gson.annotations.SerializedName

data class SetPasswordPostData(

    @SerializedName("password")
    val password: String

)