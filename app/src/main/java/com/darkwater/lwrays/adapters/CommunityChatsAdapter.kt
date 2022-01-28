package com.darkwater.lwrays.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darkwater.lwrays.R
import com.darkwater.lwrays.models.Chat
import com.darkwater.lwrays.vh.ChatViewHolder

class CommunityChatsAdapter(val chats: MutableList<Chat>) : RecyclerView.Adapter<ChatViewHolder>() {

    private var clickListener: ((Int) -> Unit)? = null

    fun addClickListener(listener: (Int) -> Unit) {
        clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.chat_card, parent, false),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.title.text = chats[position].title
        holder.membersCount.text = "${chats[position].membersCount} участников"
        holder.loadIcon(
            chats[position].icon.resourceList.maxByOrNull { it.width }!!.url
        )
    }

    override fun getItemCount(): Int {
        return chats.size
    }

}