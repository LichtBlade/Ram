package com.example.ram.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ram.databinding.ActivityAuthenticationBinding

class Authentication : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            if(validation()){
                Toast.makeText(this,"naysuu",Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun validation():Boolean{
        val studentID = binding.etStudentId.text.toString()
        val password = binding.etPassword.text.toString()

        if(studentID.isEmpty()){
            binding.etStudentId.error = "Please enter Student ID"
            return false
        }
        if(password.isEmpty()){
            binding.etPassword.error = "Please enter password"
            return false
        }

        return true
    }
}