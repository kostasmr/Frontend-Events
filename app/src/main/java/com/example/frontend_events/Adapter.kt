package com.example.frontend_events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_events.models.Event

class Adapter(private val events: List<Event>, private val onItemClick: (Event) -> Unit) : RecyclerView.Adapter<Adapter.PopularViewHolder>(){

    inner class PopularViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.event_image)
        val title: TextView = itemView.findViewById(R.id.event_title)
        val date: TextView = itemView.findViewById(R.id.event_date)
        val location: TextView = itemView.findViewById(R.id.event_location)
        val price: TextView = itemView.findViewById(R.id.event_price)

        fun bind(event: Event) {
            itemView.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.popular_layout, parent, false)
        return PopularViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val event = events[position]
        holder.image.setImageResource(event.imageId)
        holder.title.text = event.title
        holder.date.text = event.date
        holder.location.text = event.location
        holder.price.text = event.price

        holder.bind(event)
    }

    override fun getItemCount(): Int = events.size
}