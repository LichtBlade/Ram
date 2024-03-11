package com.example.ram.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ram.ApiService
import com.example.ram.GlobalVariables
import com.example.ram.databinding.ActivityMainBinding
import com.example.ram.details.Activity_details
import com.example.ram.helppage.HelpScreen
import com.example.ram.homepage.ActivityHome
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if the user is already logged in
        val (token, userId) = getToken()
        if (token != null) {
            // User is already logged in, navigate to home activity
            navigateToHome(token, userId.toString())
            return
        }

        binding.btnEditprofileConfirm.setOnClickListener {
            sendLoginData()
        }
        binding.tvHelp?.setOnClickListener {
            startActivity(Intent(this, HelpScreen::class.java))
        }
    }

    private fun sendLoginData() {
        val user_id = binding.etStudentID.text.toString()
        val password = binding.etPassword.text.toString()

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://64.23.183.4/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val apiService = retrofit.create(ApiService::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.login(user_id, password)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse != null) {
                            // Store token and user id securely
                            saveToken(loginResponse.token.toString())
                            saveUserId(user_id)

                            // Navigate to home activity
                            navigateToHome(loginResponse.token.toString(), user_id)
                        } else {
                            // Handle null response
                            Toast.makeText(this@MainActivity, "Error: Null response body", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Handle HTTP error
                        Toast.makeText(this@MainActivity, "Error: Invalid Student ID or Password", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("MainActivity", "Error: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun navigateToHome(token: String, userId: String) {
        GlobalVariables.token = token // Store token globally for later use if needed
        val intent = Intent(this, ActivityHome::class.java)
        intent.putExtra("creator_id", userId)
        startActivity(intent)
        finish()
    }

    // Storing token in SharedPreferences
    private fun saveToken(token: String) {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }

    // Storing user id in SharedPreferences
    private fun saveUserId(userId: String) {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user_id", userId)
        editor.apply()
    }

    // Retrieve token and user id from SharedPreferences
    private fun getToken(): Pair<String?, String?> {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        val userId = sharedPreferences.getString("user_id", null)
        return Pair(token, userId)
    }
}