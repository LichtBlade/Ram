package com.example.ram.homepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.ram.R
import com.example.ram.databinding.ActivityActitvityUpdateBinding

class ActivityUpdate : AppCompatActivity() {
    private lateinit var binding: ActivityActitvityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityActitvityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}