package com.example.ram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ram.databinding.ActivityScheduleBinding

class activity_schedule : AppCompatActivity() {
    private lateinit var binding: ActivityScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}