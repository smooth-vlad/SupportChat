package ru.scid.supportchat.domain.entities.auth

import com.google.gson.annotations.SerializedName

data class CreateUserPostData(

    @SerializedName("user")
    val user: User

) {
    data class User(
        @SerializedName("name")
        val name: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("role")
        val role: String,
    )
}