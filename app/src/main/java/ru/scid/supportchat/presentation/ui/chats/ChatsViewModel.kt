package ru.scid.supportchat.presentation.ui.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.scid.supportchat.domain.entities.tickets.TicketData
import ru.scid.supportchat.domain.repositories.TicketsRepository
import ru.scid.supportchat.presentation.base.BaseViewModel
import ru.scid.supportchat.util.Constants
import ru.scid.supportchat.util.Resource
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(private val ticketsRepository: TicketsRepository) : BaseViewModel() {

    private val _tickets = MutableLiveData<List<TicketData>>()
    val tickets: LiveData<List<TicketData>> = _tickets

    fun listTickets() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val response = ticketsRepository.listTickets()
            _isLoading.postValue(false)
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.let {
                        _tickets.postValue(response.data.tickets)
                    } ?: run {
                        _onError.emit(Constants.ERROR_MESSAGE)
                    }
                }
                Resource.Status.ERROR -> {
                    _onError.emit(response.message ?: Constants.ERROR_MESSAGE)
                }
            }
        }
    }

}