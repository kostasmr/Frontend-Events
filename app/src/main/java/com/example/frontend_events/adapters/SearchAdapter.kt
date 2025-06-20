package com.example.frontend_events.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.frontend_events.R
import com.example.frontend_events.models.Event
import com.example.frontend_events.ApiInterface
import com.example.frontend_events.activity.BASE_URL
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class SearchAdapter (private var events: List<Event>, private val onItemClick: (Event) -> Unit) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.search_image)
        val title: TextView = itemView.findViewById(R.id.search_title)
        val location: TextView = itemView.findViewById(R.id.search_location)
        val date: TextView = itemView.findViewById(R.id.search_date)
        val heartIcon: ImageView = itemView.findViewById(R.id.heartBtn)

        fun bind(event: Event) {
            itemView.setOnClickListener {
                onItemClick(event)
            }

            updateHeartIcon(event.favorited)

            heartIcon.setOnClickListener {
                val newStatus = !event.favorited
                val api = getApiService()
                val body = mapOf("eventId" to event.eventId)

                if (newStatus) {
                    api.addFavorite(body).enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                event.favorited = true
                                updateHeartIcon(true)
                            } else {
                                Toast.makeText(itemView.context, "Already in favorites", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(itemView.context, "Failed to favorite", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    api.removeFavorite(body).enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                event.favorited = false
                                updateHeartIcon(false)
                            } else {
                                Toast.makeText(itemView.context, "Failed to unfavorite", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(itemView.context, "Error removing favorite", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }

        private fun updateHeartIcon(isFavorited: Boolean) {
            val icon = if (isFavorited) R.drawable.ic_heart_pink else R.drawable.ic_heart
            heartIcon.setImageResource(icon)
        }

        private fun getApiService(): ApiInterface {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
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