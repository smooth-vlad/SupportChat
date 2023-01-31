package ru.scid.supportchat.presentation.ui.chats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.scid.supportchat.databinding.ListItemChatBinding
import ru.scid.supportchat.domain.entities.tickets.TicketData

class ChatsListAdapter(private val onClick: ((ticket: TicketData) -> Unit)? = null):
    ListAdapter<(TicketData), ChatsListAdapter.ViewHolder>(ChatDiffCallback()) {

    inner class ViewHolder(private val binding: ListItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TicketData) {
            binding.displayNameText.text = item.subject
            binding.statusText.text = item.status
            binding.root.setOnClickListener { onClick?.invoke(item) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemChatBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }
}

class ChatDiffCallback : DiffUtil.ItemCallback<TicketData>() {
    override fun areItemsTheSame(oldItem: TicketData, itemWithUserInfo: TicketData): Boolean {
        return oldItem == itemWithUserInfo
    }

    override fun areContentsTheSame(oldItem: TicketData, itemWithUserInfo: TicketData): Boolean {
        return oldItem.id == itemWithUserInfo.id
    }
}