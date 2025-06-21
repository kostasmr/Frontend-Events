package com.example.frontend_events.activity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.frontend_events.ApiInterface
import com.example.frontend_events.AppState
import com.example.frontend_events.R
import com.example.frontend_events.activity.MainActivity
import com.example.frontend_events.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var credentials: List<String>
    private lateinit var name: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val nameInput = findViewById<EditText>(R.id.username_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)
        val loginBtn = findViewById<TextView>(R.id.loginBtn)

        loginBtn.setOnClickListener {
            name = nameInput.text.toString().trim()
            password = passwordInput.text.toString().trim()

            if (name.isEmpty()) {
                nameInput.error = "Name is required"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                passwordInput.error = "Password is required"
                return@setOnClickListener
            }

            val credentials = mapOf(
                "name" to name,
                "password" to password
            )

            loginMyUser(credentials) { user ->
                Log.d("LoginSuccess", "User logged in: $user")
                Toast.makeText(this, "Welcome ${user.name}", Toast.LENGTH_SHORT).show()
                AppState.currentUser = user
                val intent = Intent(this, SelectCategoryAvtivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val signupBtn = findViewById<TextView>(R.id.signupBtn)

        signupBtn.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun loginMyUser(credentials: Map<String, String>,onResult: (User) -> Unit){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.loginUser(credentials)


        retrofitData.enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val loggedInUser = response.body()
                if (loggedInUser != null) {
                    onResult(loggedInUser)
                } else {
                    Toast.makeText(this@LoginActivity, "False credentials", Toast.LENGTH_SHORT).show()
                    Log.d("LoginActivity", "Login failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("LoginActivity", "onFailure: ${t.message}")
            }
        })
    }
}