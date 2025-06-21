package com.example.frontend_events.activity

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

        home.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
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
    }
}