package com.darkwater.lwrays.vh

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.darkwater.lwrays.R
import com.squareup.picasso.Picasso

class CommunityViewHolder(itemView: View, clickListener: ((Int) -> Unit)?) : RecyclerView.ViewHolder(itemView) {
    private val icon: ImageView = itemView.findViewById(R.id.communityIcon)
    val name: TextView = itemView.findViewById(R.id.communityName)
    init {
        itemView.setOnClickListener { if (clickListener != null) clickListener(adapterPosition) }
    }
    fun loadIcon(url: String) {
        Picasso.get().load(url).into(icon)
    }
}