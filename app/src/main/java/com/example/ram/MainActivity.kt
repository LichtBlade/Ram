package com.example.ram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ram.databinding.ActivityMainBinding
import com.example.ram.helppage.HelpScreen
import com.example.ram.homepage.ActivityHome

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEditprofileConfirm.setOnClickListener {
            if(validation()){
                startActivity(Intent(this, ActivityHome::class.java))
                finish()
            }
        }
        binding.tvHelp?.setOnClickListener {
            startActivity(
                Intent(this, HelpScreen::class.java)
            )
        }
    }

    private fun validation():Boolean{
        val student = binding.etStudentID?.text.toString()
        val password = binding.etPassword?.text.toString()

        if(student.isEmpty()){
            binding.etStudentID?.error = "Please fill up"
            return false
        }

        if (password.isEmpty()){
            binding.etPassword?.error = "Please fill up"
            return false
        }

        return true
    }
}