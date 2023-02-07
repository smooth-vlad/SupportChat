package ru.scid.supportchat.presentation.ui.chats.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import ru.scid.supportchat.domain.entities.comments.TicketCommentData
import ru.scid.supportchat.domain.repositories.TicketsRepository
import ru.scid.supportchat.domain.repositories.UserDataRepository
import ru.scid.supportchat.presentation.base.BaseViewModel
import ru.scid.supportchat.util.Constants
import ru.scid.supportchat.util.Resource

class ChatViewModel @AssistedInject constructor(
    @Assisted("ticketId") private val ticketId: Long,
    private val ticketsRepository: TicketsRepository,
    private val userDataRepository: UserDataRepository
) :
    BaseViewModel() {

    private val _ticketComments = MutableLiveData<List<TicketCommentData>>()
    val ticketComments: LiveData<List<TicketCommentData>> = _ticketComments

    private val _onCommentSent = MutableSharedFlow<Unit>()
    val onCommentSent: SharedFlow<Unit> = _onCommentSent

    private var message = ""

    val userId = userDataRepository.id

    init {
        listTicketComments()
    }

    private fun listTicketComments() {
        viewModelScope.launch {
            while (true) {
                val response = ticketsRepository.listTicketComments(ticketId)
                when (response.status) {
                    Resource.Status.SUCCESS -> {
                        _ticketComments.postValue(response.data?.tickets)
                    }
                    Resource.Status.ERROR -> {
                        _onError.emit(response.message ?: Constants.ERROR_MESSAGE)
                    }
                }
                delay(5000)
            }
        }
    }

    fun onSendClick() {
        createTicket()
    }

    private fun createTicket() {
        viewModelScope.launch {
            ticketsRepository.createTicketComment(ticketId, message)
            _onCommentSent.emit(Unit)
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