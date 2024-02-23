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

        binding.btnEditprofileConfirm.setOnClickListener {
            sendLoginData(this@MainActivity)
        }
        binding.tvHelp?.setOnClickListener {
            startActivity(
                Intent(this, Activity_details::class.java)
            )
        }
    }

    private fun sendLoginData(context: Context) {
        val user_id = binding.etStudentID.text.toString()
        val password = binding.etPassword.text.toString()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.login(user_id, password)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        if (response.code() == 200) { // Check HTTP status code
                            // Handle successful login
                            val loginResponse = response.body()
                            GlobalVariables.userID = loginResponse?.userId
                            GlobalVariables.loginTime = loginResponse?.loginTime

                            Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                            val intent = Intent(context, ActivityHome::class.java)
                            intent.putExtra("creator_id", user_id)
                            context.startActivity(intent)
                            (context as Activity).finish()
                        } else {
                            // Handle other HTTP status codes
                            Toast.makeText(context, "Invalid response code: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Handle HTTP error
                        Toast.makeText(context, "Error: Invalid Student ID or Password", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("MainActivity", "Error: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}