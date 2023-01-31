package ru.scid.supportchat.presentation.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.scid.supportchat.databinding.ListItemMessageBinding
import ru.scid.supportchat.databinding.ListItemMessageSentBinding
import ru.scid.supportchat.domain.entities.comments.TicketCommentData

class MessagesListAdapter : ListAdapter<TicketCommentData, RecyclerView.ViewHolder>(
    MessageDiffCallback()
) {

    private val holderTypeMessageReceived = 1
    private val holderTypeMessageSent = 2

    class ReceivedViewHolder(private val binding: ListItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TicketCommentData) {
            binding.messageText.text = item.body
            binding.timeText.text = item.createdAt
        }
    }

    class SentViewHolder(private val binding: ListItemMessageSentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TicketCommentData) {
            binding.messageText.text = item.body
            binding.timeText.text = item.createdAt
        }
    }

    override fun getItemViewType(position: Int): Int {
        return holderTypeMessageSent
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

    override fun areContentsTheSame(oldItem: TicketCommentData, newItem: TicketCommentData): Boolean {
        return oldItem.id == newItem.id
    }
}
