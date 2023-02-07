package ru.scid.supportchat.domain.entities.auth

import com.google.gson.annotations.SerializedName

data class CreateUserResponse(
    @SerializedName("user")
    val user: User,
) {
    data class User(
        @SerializedName("id")
        val id: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("email")
        val email: String,
    )
}