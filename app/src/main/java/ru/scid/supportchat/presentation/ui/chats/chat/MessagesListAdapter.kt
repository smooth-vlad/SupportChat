package ru.scid.supportchat.presentation.ui.chats.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.scid.supportchat.databinding.ListItemMessageBinding
import ru.scid.supportchat.databinding.ListItemMessageSentBinding
import ru.scid.supportchat.domain.entities.comments.TicketCommentData
import ru.scid.supportchat.util.Constants
import ru.scid.supportchat.util.DateUtil

class MessagesListAdapter(private val userId: Long) :
    ListAdapter<TicketCommentData, RecyclerView.ViewHolder>(
        MessageDiffCallback()
    ) {

    private val holderTypeMessageReceived = 1
    private val holderTypeMessageSent = 2

    inner class ReceivedViewHolder(private val binding: ListItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TicketCommentData) {
            binding.messageText.text = item.body
            binding.timeText.text = getCreationDate(item)
        }
    }

    private fun getCreationDate(item: TicketCommentData): String? {
        val date = item.createdAt.substringBeforeLast('Z')
        return DateUtil.formatDate(date, Constants.DATE_TIME_WITHOUT_MILLIS_FORMAT, "HH:mm:ss")
    }

    inner class SentViewHolder(private val binding: ListItemMessageSentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TicketCommentData) {
            binding.messageText.text = item.body
            binding.timeText.text = getCreationDate(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).authorId == userId) {
            holderTypeMessageSent
        } else {
            holderTypeMessageReceived
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            holderTypeMessageSent -> (holder as SentViewHolder).bind(
                getItem(position)
            )

            holderTypeMessageReceived -> (holder as ReceivedViewHolder).bind(
                getItem(position)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            holderTypeMessageSent -> {
                val binding = ListItemMessageSentBinding.inflate(layoutInflater, parent, false)
                SentViewHolder(binding)
            }

            holderTypeMessageReceived -> {
                val binding = ListItemMessageBinding.inflate(layoutInflater, parent, false)
                ReceivedViewHolder(binding)
            }

            else -> {
                throw Exception("Error reading holder type")
            }
        }
    }
}

class MessageDiffCallback : DiffUtil.ItemCallback<TicketCommentData>() {
    override fun areItemsTheSame(oldItem: TicketCommentData, newItem: TicketCommentData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: TicketCommentData,
        newItem: TicketCommentData
    ): Boolean {
        return oldItem.id == newItem.id
    }
}
