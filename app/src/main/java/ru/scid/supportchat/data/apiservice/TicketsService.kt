package ru.scid.supportchat.data.apiservice

import retrofit2.Response
import retrofit2.http.*
import ru.scid.supportchat.domain.entities.comments.CreateTicketCommentPutData
import ru.scid.supportchat.domain.entities.comments.ListTicketCommentsData
import ru.scid.supportchat.domain.entities.tickets.CreateTicketData
import ru.scid.supportchat.domain.entities.tickets.CreateTicketPostData
import ru.scid.supportchat.domain.entities.tickets.ListTicketsData

interface TicketsService {

    @POST("requests.json")
    suspend fun createTicket(@Body ticketData: CreateTicketPostData): Response<CreateTicketData>

    @PUT("requests/{ticket_id}.json")
    suspend fun createTicketComment(@Path("ticket_id") ticketId: Long, @Body ticketData: CreateTicketCommentPutData): Response<*>

    @GET("requests/{ticket_id}/comments")
    suspend fun listTicketComments(@Path("ticket_id") ticketId: Long): Response<ListTicketCommentsData>

    @GET("requests")
    suspend fun listTickets(): Response<ListTicketsData>

}