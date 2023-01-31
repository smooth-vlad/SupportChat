package ru.scid.supportchat.domain.repositories

import retrofit2.Response
import ru.scid.supportchat.data.apiservice.TicketsService
import ru.scid.supportchat.domain.entities.comments.CreateTicketCommentData
import ru.scid.supportchat.domain.entities.comments.CreateTicketCommentPutData
import ru.scid.supportchat.domain.entities.comments.ListTicketCommentsData
import ru.scid.supportchat.domain.entities.comments.TicketCommentData
import ru.scid.supportchat.domain.entities.tickets.*
import javax.inject.Inject

class TicketsRepository @Inject constructor(private val ticketsService: TicketsService) {

    suspend fun createTicket(subject: String, body: String): CreateTicketData? {
        return ticketsService.createTicket(
            ticketData = CreateTicketPostData(
                ticket = TicketPostData(
                    subject,
                    comment = CommentPostData(
                        body
                    )
                )
            )
        ).body()
    }

    suspend fun createTicketComment(ticketId: Long, body: String) {
        ticketsService.createTicketComment(ticketId, CreateTicketCommentPutData(
            CreateTicketCommentData(
                CommentPostData(
                    body
                )
            )
        ))
    }

    suspend fun listTicketComments(ticketId: Long): ListTicketCommentsData? {
        return ticketsService.listTicketComments(ticketId).body()
    }

    suspend fun listTickets(): ListTicketsData? {
        return ticketsService.listTickets().body()
    }

}