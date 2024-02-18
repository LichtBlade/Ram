package com.example.ram

import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.Spinner
import android.widget.Toast
import com.example.ram.databinding.ActivityScheduleBinding

class activity_schedule : AppCompatActivity() {
    private lateinit var binding: ActivityScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_schedule)

        setupSpinner()
        nextButton()
    }

    private fun setupSpinner() {
        val spinner: Spinner = findViewById(R.id.dropdownSpinner)
        val items = arrayOf(
            "8:00 - 9:00 AM",
            "9:00 - 10:00 AM",
            "10:00 - 11:00 AM",
            "1:00 - 2:00 PM",
            "3:00 - 4:00 PM"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }


    private fun nextButton() {
        val nextButton = findViewById<Button>(R.id.btn_next)

        val calendarView = findViewById<CalendarView>(R.id.cv_calendar)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val selectedDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

            nextButton.setOnClickListener {
                if (selectedDayOfWeek == Calendar.SATURDAY || selectedDayOfWeek == Calendar.SUNDAY) {
                    Toast.makeText(
                        this,
                        "Invalid date selected for Saturday/Sunday",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this, "Validation successful.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}