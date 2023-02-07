package ru.scid.supportchat.presentation.ui.auth

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import ru.scid.supportchat.domain.entities.auth.CreateUserResponse
import ru.scid.supportchat.domain.repositories.UserDataRepository
import ru.scid.supportchat.domain.repositories.UsersRepository
import ru.scid.supportchat.presentation.base.BaseViewModel
import ru.scid.supportchat.util.Constants
import ru.scid.supportchat.util.Resource
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val userDataRepository: UserDataRepository
) :
    BaseViewModel() {

    private var name: String = ""
    private var email: String = ""
    private var password: String = ""

    private val _onAuthSuccess = MutableSharedFlow<Unit>()
    val onAuthSuccess: SharedFlow<Unit> = _onAuthSuccess

    fun onNameChanged(value: String) {
        name = value
    }

    fun onEmailChanged(value: String) {
        email = value
    }

    fun onPasswordChanged(value: String) {
        password = value
    }

    fun auth() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val response = usersRepository.createUser(name, email)
            _isLoading.postValue(false)
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    onRegistrationSuccess(response)
                }
                Resource.Status.ERROR -> {
                    _onError.emit(response.message ?: Constants.ERROR_MESSAGE)
                }
            }
        }
    }

    private suspend fun onRegistrationSuccess(response: Resource<CreateUserResponse>) {
        response.data?.user?.id?.let {
            _isLoading.postValue(true)
            setPassword(it)
            _isLoading.postValue(false)
        }
    }

    private suspend fun setPassword(userId: Long) {
        try {
            usersRepository.setPassword(userId, password)
            setUserData(userId)

            _onAuthSuccess.emit(Unit)
        } catch (ex: Exception) {
            _onError.emit(Constants.ERROR_MESSAGE)
        }
    }

    private fun setUserData(userId: Long) {
        userDataRepository.email = email
        userDataRepository.id = userId
        userDataRepository.password = password
        userDataRepository.useClientAuth = true
    }

}