package com.darkwater.lwrays.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darkwater.lwrays.R
import com.darkwater.lwrays.models.Circle
import com.darkwater.lwrays.vh.CommunityViewHolder

class CommunityListAdapter(val circles: List<Circle>) : RecyclerView.Adapter<CommunityViewHolder>() {

    private var clickListener: ((Int) -> Unit)? = null

    fun addClickListener(listener: (Int) -> Unit) {
        clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        return CommunityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.community_card, parent, false),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        holder.name.text = circles[position].name
        holder.loadIcon(
            circles[position].icon.resourceList.maxByOrNull { it.width }!!.url
        )
    }

    override fun getItemCount(): Int {
        return circles.size
    }

}