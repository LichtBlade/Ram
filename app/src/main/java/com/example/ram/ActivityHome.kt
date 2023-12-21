package com.example.ram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ram.databinding.ActivityHomeBinding

class ActivityHome : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}