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

class FavoritesAdapter(
    private var favoriteEvents: List<Event>,
    private val onItemClick: (Event) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    inner class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.fav_event_image)
        val title: TextView = itemView.findViewById(R.id.fav_event_title)
        val location: TextView = itemView.findViewById(R.id.fav_event_location)
        val price: TextView = itemView.findViewById(R.id.fav_event_price)

        fun bind(event: Event) {
            itemView.setOnClickListener { onItemClick(event) }

            title.text = event.title
            location.text = event.schedule[0].location
            price.text = String.format("$%02d.00", event.ticketTypes[0].price)

            Glide.with(itemView.context)
                .load(event.image)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorites_layout, parent, false)
        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(favoriteEvents[position])
    }

    override fun getItemCount(): Int = favoriteEvents.size

    fun updateData(newFavorites: List<Event>) {
        favoriteEvents = newFavorites
        notifyDataSetChanged()
    }
}
