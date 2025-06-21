package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.frontend_events.ApiInterface
import com.example.frontend_events.AppState
import com.example.frontend_events.R
import com.example.frontend_events.models.Event
import com.example.frontend_events.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupActivity : AppCompatActivity() {

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val signup = findViewById<AppCompatButton>(R.id.signupBtn)
        val nameInput = findViewById<EditText>(R.id.username_input)
        val emailInput = findViewById<EditText>(R.id.email_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)

        signup.setOnClickListener {
            name = nameInput.text.toString().trim()
            email = emailInput.text.toString().trim()
            password = passwordInput.text.toString().trim()

            if (name.isEmpty()) {
                nameInput.error = "Name is required"
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailInput.error = "Enter a valid email"
                return@setOnClickListener
            }

            if (password.length < 6) {
                passwordInput.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            user = User( name = name, email = email, password = password)

            createMyUser(user) { createdUser ->
                Toast.makeText(this, "${createdUser.name} signed up successfully", Toast.LENGTH_SHORT).show()
                // For example, navigate to another activity or update UI
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        val loginPage = findViewById<TextView>(R.id.loginBtn)

        loginPage.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun createMyUser(user: User,onResult: (User) -> Unit){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.createUser(user)


        retrofitData.enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val createdUser = response.body()
                if (createdUser != null) {
                    onResult(createdUser)
                } else {
                    if (response.code() == 409) {
                        // Show Toast for username already exists
                        Toast.makeText(this@SignupActivity, "Username already exists", Toast.LENGTH_SHORT).show()
                    } else {
                        // Generic error
                        Toast.makeText(this@SignupActivity, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                    }
                    Log.d("SignupActivity", "User creation failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("SignupActivity", "onFailure: ${t.message}")
            }
        })
    }
}