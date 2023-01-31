package ru.scid.supportchat.presentation.ui.createChat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import ru.scid.supportchat.domain.entities.tickets.TicketData
import ru.scid.supportchat.domain.repositories.TicketsRepository
import javax.inject.Inject

@HiltViewModel
class CreateChatViewModel @Inject constructor(private val ticketsRepository: TicketsRepository) : ViewModel() {

    private var subject = ""
    private var description = ""

    var ticketCreated = MutableSharedFlow<TicketData>()

    fun onSubjectTextChanged(subject: String) {
        this.subject = subject
    }

    fun onDescriptionTextChanged(description: String) {
        this.description = description
    }

    fun onCreateClicked() {
        viewModelScope.launch {
            ticketsRepository.createTicket(subject, description)?.let {
                ticketCreated.emit(it.ticket)
            }
        }
    }

}