package com.example.frontend_events.adapters

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_events.models.ChatMessage
import com.example.frontend_events.R

class ChatAdapter(private val messages: MutableList<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>()  {

    class ChatViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.messageText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_layout, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        holder.textView.text = message.text

        // Set background depending on who sent it
        val background = if (message.isUser) R.drawable.profile_message_shape else R.drawable.user_message_shape
        holder.textView.setBackgroundResource(background)

        val textColor = if (message.isUser) Color.WHITE else Color.BLACK
        holder.textView.setTextColor(textColor)

        // Align using gravity
        val container = holder.itemView.findViewById<LinearLayout>(R.id.messageContainer)
        container.gravity = if (message.isUser) Gravity.END else Gravity.START

        val layoutParams = holder.textView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.marginStart = if (message.isUser) 100 else 0
        layoutParams.marginEnd = if (message.isUser) 0 else 100
        holder.textView.layoutParams = layoutParams
    }

    override fun getItemCount() = messages.size
}

