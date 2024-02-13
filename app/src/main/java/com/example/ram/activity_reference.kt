package com.example.ram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ram.databinding.ActivityReferenceBinding
import com.example.ram.databinding.ActivityScheduleBinding

class activity_reference : AppCompatActivity() {
    private lateinit var binding: ActivityReferenceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReferenceBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_reference)
    }
}