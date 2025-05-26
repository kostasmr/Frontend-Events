package com.example.frontend_events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_events.RecomAdapter.RecomViewHolder
import com.example.frontend_events.models.Event

class RecomAdapter(private val events: List<Event>, private val onItemClick: (Event) -> Unit) : RecyclerView.Adapter<RecomAdapter.RecomViewHolder>() {

    inner class RecomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.recom_image)
        val title: TextView = itemView.findViewById(R.id.recom_title)
        val location: TextView = itemView.findViewById(R.id.recom_location)
        val price: TextView = itemView.findViewById(R.id.recom_price)

        fun bind(event: Event) {
            itemView.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recom_layout, parent, false)
        return RecomViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecomAdapter.RecomViewHolder, position: Int) {
        val event = events[position]
        holder.image.setImageResource(event.imageId)
        holder.title.text = event.title
        holder.location.text = event.location
        holder.price.text = event.price

        holder.bind(event)
    }

    override fun getItemCount(): Int = events.size


}