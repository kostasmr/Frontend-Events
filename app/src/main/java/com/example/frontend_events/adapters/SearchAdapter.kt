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

class SearchAdapter (private var events: List<Event>, private val onItemClick: (Event) -> Unit) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.search_image)
        val title: TextView = itemView.findViewById(R.id.search_title)
        val location: TextView = itemView.findViewById(R.id.search_location)
        val date: TextView = itemView.findViewById(R.id.search_date)

        fun bind(event: Event) {
            itemView.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_layout, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val event = events[position]

        Glide.with(holder.itemView.context)
            .load(event.image)
            .into(holder.image)
        holder.title.text = event.title
        holder.location.text = event.schedule[0].location
        holder.date.text = event.schedule[0].date

        holder.bind(event)
    }

    override fun getItemCount(): Int = events.size

    fun updateData(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }

}