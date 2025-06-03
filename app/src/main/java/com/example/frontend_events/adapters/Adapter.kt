package com.example.frontend_events.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.frontend_events.R
import com.example.frontend_events.models.Event

class Adapter(private var events: List<Event>, private val onItemClick: (Event) -> Unit) : RecyclerView.Adapter<Adapter.PopularViewHolder>(){

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

        Glide.with(holder.itemView.context)
            .load(event.image)
            .into(holder.image)

        holder.title.text = event.title
        holder.date.text = event.schedule[0].date
        holder.location.text = event.schedule[0].location
//        holder.price.text = (event.ticketTypes[0].price).toString()
        holder.price.text = String.format("$%02d.00", event.ticketTypes[0].price)

        holder.bind(event)
    }

    fun updateData(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = events.size
}