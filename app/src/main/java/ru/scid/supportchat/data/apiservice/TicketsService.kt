package ru.scid.supportchat.data.apiservice

import retrofit2.Response
import retrofit2.http.*
import ru.scid.supportchat.domain.entities.comments.CreateTicketCommentPutData
import ru.scid.supportchat.domain.entities.comments.ListTicketCommentsData
import ru.scid.supportchat.domain.entities.comments.TicketCommentData
import ru.scid.supportchat.domain.entities.tickets.CreateTicketData
import ru.scid.supportchat.domain.entities.tickets.CreateTicketPostData
import ru.scid.supportchat.domain.entities.tickets.ListTicketsData
import ru.scid.supportchat.domain.entities.tickets.TicketData

interface TicketsService {

    @POST("tickets.json")
    suspend fun createTicket(@Body ticketData: CreateTicketPostData): Response<CreateTicketData>

    @PUT("tickets/{ticket_id}.json")
    suspend fun createTicketComment(@Path("ticket_id") ticketId: Long, @Body ticketData: CreateTicketCommentPutData): Response<*>

    @GET("tickets/{ticket_id}/comments")
    suspend fun listTicketComments(@Path("ticket_id") ticketId: Long): Response<ListTicketCommentsData>

    @GET("tickets")
    suspend fun listTickets(): Response<ListTicketsData>

}