package com.example.frontend_events.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.frontend_events.AppState
import com.example.frontend_events.R
import com.example.frontend_events.models.User

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        var user = AppState.currentUser

        //menu bar
        val home = findViewById<ImageView>(R.id.homeBtn)
        val ticketsBtn = findViewById<ImageView>(R.id.ticketsBtn)
        val favoritesBtn = findViewById<ImageView>(R.id.favoritesBtn)

        home.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        ticketsBtn.setOnClickListener {
            val intent = Intent(this, TicketListActivity::class.java)
            startActivity(intent)
            finish()
        }

        val editBtn = findViewById<AppCompatButton>(R.id.editBtn)

        editBtn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        val userName = findViewById<TextView>(R.id.userName)
        userName.setText(user?.name)

        val aboutText = findViewById<TextView>(R.id.aboutText)
        if ((user?.about).isNullOrEmpty()){
            aboutText.setText("Update your about.")
        }else{
            aboutText.setText(user.about)
        }


        // Events gallery
        val imageView1 = findViewById<ImageView>(R.id.imageView28)
        val imageView2 = findViewById<ImageView>(R.id.imageView29)
        val imageView3 = findViewById<ImageView>(R.id.imageView30)
        val imageView4 = findViewById<ImageView>(R.id.imageView31)

        imageView1.setOnClickListener {
            showImagePopup(R.drawable.img_onboarding1)
        }

        imageView2.setOnClickListener {
            showImagePopup(R.drawable.popular2)
        }

        imageView3.setOnClickListener {
            showImagePopup(R.drawable.img_recom1)
        }

        imageView4.setOnClickListener {
            showImagePopup(R.drawable.img_onboarding6)
        }
    }

    private fun showImagePopup(imageResId: Int) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.events_gallery_popup)

        val imageView = dialog.findViewById<ImageView>(R.id.popupImageView)
        imageView.setImageResource(imageResId)

        // Close dialog when user taps the image
        imageView.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}