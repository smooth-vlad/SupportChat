package ru.scid.supportchat.presentation.ui.chats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.scid.supportchat.domain.entities.tickets.TicketData
import ru.scid.supportchat.domain.repositories.TicketsRepository
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(private val ticketsRepository: TicketsRepository) : ViewModel() {

    val tickets = MutableLiveData<List<TicketData>>()

    init {
        listTickets()
    }

    private fun listTickets() {
        viewModelScope.launch {
            tickets.postValue(ticketsRepository.listTickets()?.tickets)
        }
    }

}