package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.frontend_events.AppState
import com.example.frontend_events.R

class MainActivity : AppCompatActivity() {


    private lateinit var next: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = AppState.currentUser

        Thread.sleep(2000)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        next = findViewById(R.id.startBtn)

        next.setOnClickListener {
            if(user != null){
                val intent = Intent(this@MainActivity, SelectCategoryAvtivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}