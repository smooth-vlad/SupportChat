package ru.scid.supportchat.data.apiservice

import retrofit2.Response
import retrofit2.http.*
import ru.scid.supportchat.domain.entities.auth.CreateUserPostData
import ru.scid.supportchat.domain.entities.auth.CreateUserResponse
import ru.scid.supportchat.domain.entities.auth.SetPasswordPostData

interface UsersService {

    @POST("users/{userId}/password.json")
    suspend fun setPassword(@Path("userId") userId: Long, @Body setPasswordPostData: SetPasswordPostData)

    @POST("users")
    suspend fun createUser(@Body createUserPostData: CreateUserPostData): Response<CreateUserResponse>

}