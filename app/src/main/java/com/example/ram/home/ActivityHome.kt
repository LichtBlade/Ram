package com.example.ram.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ram.databinding.ActivityActiviityHomeBinding

class ActivityHome : AppCompatActivity() {
    private lateinit var binding: ActivityActiviityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActiviityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}