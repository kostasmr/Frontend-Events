package com.example.frontend_events

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager.LayoutParams.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            FLAG_FULLSCREEN,
            FLAG_FULLSCREEN,
        )
        setContentView(R.layout.activity_splash)
        android.os.Handler().postDelayed(
            {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            },
            SPLASH_TIMER.toLong()
        )
    }
    companion object{
        private const val SPLASH_TIMER = 2000
    }
}