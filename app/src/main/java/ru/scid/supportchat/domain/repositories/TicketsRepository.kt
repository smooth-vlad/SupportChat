package ru.scid.supportchat.domain.repositories

import ru.scid.supportchat.data.apiservice.TicketsService
import ru.scid.supportchat.domain.entities.comments.CreateTicketCommentData
import ru.scid.supportchat.domain.entities.comments.CreateTicketCommentPutData
import ru.scid.supportchat.domain.entities.comments.ListTicketCommentsData
import ru.scid.supportchat.domain.entities.tickets.CommentPostData
import ru.scid.supportchat.domain.entities.tickets.CreateTicketData
import ru.scid.supportchat.domain.entities.tickets.CreateTicketPostData
import ru.scid.supportchat.domain.entities.tickets.ListTicketsData
import ru.scid.supportchat.domain.entities.tickets.TicketPostData
import ru.scid.supportchat.util.Resource
import javax.inject.Inject

class TicketsRepository @Inject constructor(private val ticketsService: TicketsService) :
    BaseRemoteRepository() {

    suspend fun createTicket(subject: String, body: String): Resource<CreateTicketData> = getResult {
        ticketsService.createTicket(
            ticketData = CreateTicketPostData(
                ticket = TicketPostData(
                    subject,
                    comment = CommentPostData(
                        body
                    )
                )
            )
        )
    }

    suspend fun createTicketComment(ticketId: Long, body: String) {
        ticketsService.createTicketComment(
            ticketId, CreateTicketCommentPutData(
                CreateTicketCommentData(
                    CommentPostData(
                        body
                    )
                )
            )
        )
    }

    suspend fun listTicketComments(ticketId: Long): Resource<ListTicketCommentsData> = getResult {
        ticketsService.listTicketComments(ticketId)
    }

    suspend fun listTickets(): Resource<ListTicketsData> = getResult {
        ticketsService.listTickets()
    }

}