package com.example.ram

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.ram.databinding.ActivityScheduleBinding
import com.example.ram.details.Activity_details
import java.text.SimpleDateFormat
import java.util.*

class activity_schedule : AppCompatActivity() {
    private lateinit var binding: ActivityScheduleBinding
    private lateinit var nextButton: Button
    private var userSelectedDate: Long = 0
    private val maxBookingsPerTimeSlot = 5  // Limit one booking per time slot
    private val maxBookingsPerDate =  35 // Limit 5 bookings per day
    private val bookedTimeSlots = mutableMapOf<String, Int>()
    private val bookedDates = mutableMapOf<String, Int>()
    private val format = SimpleDateFormat("h:mm a", Locale.US)
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
//return button
        binding.btnReturn.setOnClickListener{
            onBackPressed()
        }
        val creatorId = intent.getStringExtra("creator_id")
        val selectedPurposes = intent.getStringExtra("selectedPurposes")
        val requirements = intent.getStringExtra("requirements")

        val spinner: Spinner = binding.dropdownSpinner


        val items = arrayOf(
            "08:00 AM",
            "08:15 AM",
            "08:30 AM",
            "08:45 AM",
            "09:00 AM",
            "09:15 AM",
            "09:30 AM",
            "09:45 AM",
            "10:00 AM",
            "10:15 AM",
            "10:30 AM",
            "10:45 AM",
            "11:00 AM",
            "11:15 AM",
            "11:30 AM",
            "11:45 AM",
            "01:00 PM",
            "01:15 PM",
            "01:30 PM",
            "01:45 PM",
            "02:00 PM",
            "02:15 PM",
            "02:30 PM",
            "02:45 PM",
            "03:00 PM",
            "03:15 PM",
            "03:30 PM",
            "03:45 PM"
        )


        val calendarView = binding.cvCalendar

        // Set initial userSelectedDate to current date
        val currentDate = Calendar.getInstance().timeInMillis
        userSelectedDate = currentDate
        calendarView.minDate = currentDate

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            userSelectedDate = selectedDate.timeInMillis // Update userSelectedDate to the selected date

            val dayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK)
            // Disable Saturday and Sunday
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                calendarView.date = currentDate
                Toast.makeText(this, "We don't have transactions on weekends.", Toast.LENGTH_SHORT)
                    .show()
            }

            val selectedDateString = dateFormat.format(userSelectedDate)
            val fullyBookedTimeSlots = items.filter { time ->
                bookedTimeSlots.getOrDefault("$selectedDateString-$time", 0) >= maxBookingsPerTimeSlot
            }.toSet()

            (spinner.adapter as CustomArrayAdapter).setFullyBookedTimeSlots(fullyBookedTimeSlots)
        }

        val adapter = CustomArrayAdapter(this, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        nextButton = binding.btnNext


        nextButton.setOnClickListener {

            val selectedStartTime = spinner.selectedItem.toString()
            val selectedDate = Date(userSelectedDate)
            val selectedDateString = dateFormat.format(selectedDate)
            val selectedTimeSlot = "$selectedDateString-$selectedStartTime"

            // Check if the time slot is fully booked
            if (bookedTimeSlots.getOrDefault(selectedTimeSlot, 0) >= maxBookingsPerTimeSlot) {
                Toast.makeText(this, "This time slot is fully booked.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if the date is fully booked
            if (bookedDates.getOrDefault(selectedDateString, 0) >= maxBookingsPerDate) {
                Toast.makeText(this, "This date is fully booked.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val currentDate = Calendar.getInstance()

            if (selectedDate.after(currentDate.time)) {

                // Update the booked slots count
                val currentCount = bookedTimeSlots.getOrDefault(selectedTimeSlot, 0)
                bookedTimeSlots[selectedTimeSlot] = currentCount + 1

                // Update the booked dates count
                val currentDateCount = bookedDates.getOrDefault(selectedDateString, 0)
                bookedDates[selectedDateString] = currentDateCount + 1

                // Calculate end time
                val date = format.parse(selectedStartTime)
                val calendar = Calendar.getInstance()
                calendar.time = date!!
                // We will show in selection 1 hour only, but we will display the time in appointment details to confirm
                calendar.add(Calendar.MINUTE, 15)
                val selectedEndTime = format.format(calendar.time)

                val intent = Intent(this, Activity_details::class.java)
                intent.putExtra("selectedPurposes", selectedPurposes)
                intent.putExtra("requirements", requirements)
                intent.putExtra("paymentInfo", "paymentInfo")
                intent.putExtra("selectedDate", userSelectedDate)
                intent.putExtra("selectedStartTime", selectedStartTime)
                intent.putExtra("selectedEndTime", selectedEndTime)
                intent.putExtra("creator_id", creatorId)
                startActivity(intent)

            } else {
                Toast.makeText(
                    this,
                    "Please select a date and time that is not earlier than today.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    class CustomArrayAdapter(context: Context, private val items: Array<String>) :
        ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items) {

        private val fullyBookedTimeSlots = mutableSetOf<String>()

        fun setFullyBookedTimeSlots(timeSlots: Set<String>) {
            fullyBookedTimeSlots.clear()
            fullyBookedTimeSlots.addAll(timeSlots)
            notifyDataSetChanged()
        }

        override fun isEnabled(position: Int): Boolean {
            return !fullyBookedTimeSlots.contains(items[position])
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent) as TextView
            if (!isEnabled(position)) {
                view.setBackgroundColor(Color.GRAY)
                view.setTextColor(Color.RED)
            } else {
                view.setBackgroundColor(Color.WHITE)
                view.setTextColor(Color.BLACK)
            }
            return view
        }



    }
}
