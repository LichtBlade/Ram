package com.example.ram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ram.databinding.ActivityDetailsBinding
import com.example.ram.databinding.ActivityReferenceBinding

class activity_details : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}