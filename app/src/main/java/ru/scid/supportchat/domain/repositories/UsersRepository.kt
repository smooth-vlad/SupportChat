package ru.scid.supportchat.domain.repositories

import ru.scid.supportchat.data.apiservice.UsersService
import ru.scid.supportchat.domain.entities.auth.CreateUserPostData
import ru.scid.supportchat.domain.entities.auth.CreateUserResponse
import ru.scid.supportchat.domain.entities.auth.SetPasswordPostData
import ru.scid.supportchat.util.Resource
import javax.inject.Inject

class UsersRepository @Inject constructor(private val usersService: UsersService) :
    BaseRemoteRepository() {

    suspend fun createUser(name: String, email: String): Resource<CreateUserResponse> = getResult {
        usersService.createUser(
            CreateUserPostData(
                CreateUserPostData.User(
                    name,
                    email,
                    "end-user"
                )
            )
        )
    }

    suspend fun setPassword(id: Long, password: String) {
        usersService.setPassword(id, SetPasswordPostData(password))
    }

}