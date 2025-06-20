package com.example.frontend_events.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.frontend_events.ApiInterface
import com.example.frontend_events.R
import com.example.frontend_events.activity.BASE_URL
import com.example.frontend_events.models.Event
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class RecomAdapter(private var events: List<Event>, private val onItemClick: (Event) -> Unit) : RecyclerView.Adapter<RecomAdapter.RecomViewHolder>() {

    inner class RecomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.search_image)
        val title: TextView = itemView.findViewById(R.id.search_title)
        val location: TextView = itemView.findViewById(R.id.search_location)
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


    override fun onBindViewHolder(holder: RecomViewHolder, position: Int) {
        val event = events[position]

        Glide.with(holder.itemView.context)
            .load(event.image)
            .into(holder.image)

        holder.title.text = event.title
        holder.location.text = event.schedule[0].location
        holder.price.text = String.format("$%02d.00", event.ticketTypes[0].price)

        holder.bind(event)
    }

    fun updateData(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = events.size


}