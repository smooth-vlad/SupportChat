package ru.scid.supportchat.presentation.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.scid.supportchat.domain.entities.comments.TicketCommentData
import ru.scid.supportchat.domain.entities.tickets.CommentPostData
import ru.scid.supportchat.domain.repositories.TicketsRepository
import javax.inject.Inject

class ChatViewModel @AssistedInject constructor(
    @Assisted("ticketId") private val ticketId: Long,
    private val ticketsRepository: TicketsRepository
) :
    ViewModel() {

    val ticketComments = MutableLiveData<List<TicketCommentData>>()
    private var message = ""

    init {
        listTicketComments()
    }

    private fun listTicketComments() {
        viewModelScope.launch {
            ticketComments.postValue(ticketsRepository.listTicketComments(ticketId)?.tickets)
        }
    }

    fun onSendClick() {
        createTicket()
    }

    private fun createTicket() {
        viewModelScope.launch {
            ticketsRepository.createTicketComment(ticketId, message)
            listTicketComments()
        }
    }

    fun onMessageTextChanged(message: String) {
        this.message = message
    }

}

@AssistedFactory
interface ChatViewModelFactory {
    fun create(
        @Assisted("ticketId") id: Long,
    ): ChatViewModel
}