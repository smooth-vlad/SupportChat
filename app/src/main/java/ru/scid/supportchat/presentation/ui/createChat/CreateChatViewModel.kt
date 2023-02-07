package ru.scid.supportchat.presentation.ui.createChat

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import ru.scid.supportchat.domain.entities.tickets.TicketData
import ru.scid.supportchat.domain.repositories.TicketsRepository
import ru.scid.supportchat.presentation.base.BaseViewModel
import ru.scid.supportchat.util.Constants
import ru.scid.supportchat.util.Resource
import javax.inject.Inject

@HiltViewModel
class CreateChatViewModel @Inject constructor(private val ticketsRepository: TicketsRepository) :
    BaseViewModel() {

    private var subject = ""
    private var description = ""

    private val _ticketCreated = MutableSharedFlow<TicketData>()
    val ticketCreated: SharedFlow<TicketData> = _ticketCreated

    fun onSubjectTextChanged(subject: String) {
        this.subject = subject
    }

    fun onDescriptionTextChanged(description: String) {
        this.description = description
    }

    fun onCreateClicked() {
        viewModelScope.launch {
            val response = ticketsRepository.createTicket(subject, description)
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.let {
                        _ticketCreated.emit(it.ticket)
                    } ?: run {
                        _onError.emit(response.message ?: Constants.ERROR_MESSAGE)
                    }
                }

                Resource.Status.ERROR -> {
                    _onError.emit(response.message ?: Constants.ERROR_MESSAGE)
                }
            }
        }
    }

}