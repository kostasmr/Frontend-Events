package com.example.frontend_events.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.frontend_events.R
import com.example.frontend_events.adapters.ChatAdapter
import com.example.frontend_events.models.ChatMessage
import com.example.frontend_events.models.Event
import java.io.Serializable

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageView
    private lateinit var adapter: ChatAdapter
    private lateinit var groupTitle: TextView
    private lateinit var groupImage: ImageView
    lateinit var suggestionContainer: LinearLayout
    private lateinit var event: Event


    private val messages = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        event = intent.getSerializableExtra("event") as Event

        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageInput = findViewById(R.id.editText)
        sendButton = findViewById(R.id.imageView12)
        groupTitle = findViewById(R.id.group_title)
        groupImage = findViewById(R.id.group_image)

        groupTitle.text = event.title
        Glide.with(this)
            .load(event.image)
            .into(groupImage)

        adapter = ChatAdapter(messages)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = adapter

        sendButton.setOnClickListener {
            val text = messageInput.text.toString().trim()
            if (text.isNotEmpty()) {
                messages.add(ChatMessage(text, true)) // user message
                adapter.notifyItemInserted(messages.size - 1)
                messageInput.text.clear()
                chatRecyclerView.scrollToPosition(messages.size - 1)


                // Respond after a delay
                chatRecyclerView.postDelayed({
                    val (botReply, nextSuggestions) = getBotReply(text)
                    messages.add(ChatMessage(botReply, isUser = false))
                    adapter.notifyItemInserted(messages.size - 1)
                    chatRecyclerView.scrollToPosition(messages.size - 1)

                    showSuggestions(nextSuggestions)
                }, 500)
            }
        }

        val suggestions = listOf(
            "Hello",
            "Hi",
            "How it's going?"
        )

        suggestionContainer = findViewById<LinearLayout>(R.id.suggestionContainer)

        showSuggestions(suggestions)

        val backBtn = findViewById<ImageView>(R.id.backBtn)

        backBtn.setOnClickListener {
            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("event", event)
            startActivity(intent)
            finish()
        }
    }

    fun getBotReply(userInput: String):  Pair<String, List<String>> {

        var promo_msg = "Get ready for an unforgettable experience with ${event.title} — a ${event.category} event happening on ${event.schedule[0].date} at ${event.schedule[0].location}.\n" +
                "This exciting event features '${event.description}', offering something truly special for anyone who loves great entertainment.\n" +
                "And the best part? It's all available at an incredible price of just ${String.format("$%02d.00", event.ticketTypes[0].price)}.\n" +
                "Don’t miss your chance to be part of something amazing.\n" +
                "Book your spot now — time is precious, and this event is worth every moment."
        return when {
            userInput.contains("hello", ignoreCase = true) -> "Hi there! How can I help you?"  to listOf("Tell me for the event.")
            userInput.contains("hi", ignoreCase = true) -> "Hi there! How can I help you?" to listOf("Tell me for the event.")
            userInput.contains("how it's going?", ignoreCase = true) -> "Fine, thanks. How can I help you" to listOf("Tell me for the event.")
            userInput.contains("try again", ignoreCase = true) -> "Hi there! How can I help you?"  to listOf("Tell me for the event.")

            userInput.contains("tell me for the event.", ignoreCase = true) -> promo_msg  to listOf("Thank you.", "Goodbye!")

            userInput.contains("bye", ignoreCase = true) -> "Goodbye!" to emptyList()
            userInput.contains("Thank you.", ignoreCase = true) -> "Your welcome." to listOf("Bye")
            userInput.contains("goodbye", ignoreCase = true) -> "Goodbye!" to emptyList()
            else -> "Sorry, I don't understand that yet." to listOf("Try again", "Bye")
        }
    }

    fun showSuggestions(suggestions: List<String>) {
        suggestionContainer.removeAllViews()
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 16, 0)

        suggestions.forEach { question ->
            val button = TextView(this).apply {
                text = question
                layoutParams = params
                setBackgroundResource(R.drawable.suggestion_message_shape)
                setTextColor(ContextCompat.getColor(context, R.color.btn_color2))
                setPadding(
                    16.dpToPx(context),
                    8.dpToPx(context),
                    16.dpToPx(context),
                    8.dpToPx(context)
                )
                textSize = 14f
                setOnClickListener {
                    messageInput.setText(question)
                    sendButton.performClick()
                }
            }
            suggestionContainer.addView(button)
        }
    }

    fun Int.dpToPx(context: Context): Int =
        (this * context.resources.displayMetrics.density).toInt()
}