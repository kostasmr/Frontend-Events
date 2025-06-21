package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.frontend_events.ApiInterface
import com.example.frontend_events.AppState
import com.example.frontend_events.R
import com.example.frontend_events.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditProfileActivity : AppCompatActivity() {

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var about: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        val user = AppState.currentUser

        val backBtn = findViewById<ImageView>(R.id.backBtn)

        backBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        val nameInput = findViewById<EditText>(R.id.username_input)
        val emailInput = findViewById<EditText>(R.id.email_input)
        val aboutInput = findViewById<EditText>(R.id.about_input)

        nameInput.setText(user?.name)
        emailInput.setText(user?.email)
        aboutInput.setText(user?.about)

        val updateBtn = findViewById<AppCompatButton>(R.id.updateBtn)

        updateBtn.setOnClickListener {

            name = nameInput.text.toString().trim()
            email = emailInput.text.toString().trim()
            about = aboutInput.text.toString().trim()
            val updates = mapOf(
                "name" to name,
                "email" to email,
                "about" to about
            )

            updateMyUser(updates) { user ->
                Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show()
                AppState.currentUser = user
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    fun updateMyUser(updates: Map<String, String>,onResult: (User) -> Unit){
        val user = AppState.currentUser
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.updateUser(user?._id, updates)


        retrofitData.enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val updatedUser = response.body()
                if (updatedUser != null) {
                    onResult(updatedUser)
                } else {
                    Log.d("EditProfileActivity", "Update profile failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("EditProfileActivity", "onFailure: ${t.message}")
            }
        })
    }
}