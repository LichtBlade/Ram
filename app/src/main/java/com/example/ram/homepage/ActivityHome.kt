package com.example.ram.homepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ram.R
import com.example.ram.databinding.ActivityHomeBinding

class ActivityHome : AppCompatActivity() {
    // For Binding
    private lateinit var binding: ActivityHomeBinding

    //for RecyclerView
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<DataOfAppointmentCard>

    // Array for sample
    lateinit var referenceId : Array<String>
    lateinit var scheduleDate : Array<String>
    lateinit var scheduleTime : Array<String>
    lateinit var purpose : Array<String>
    lateinit var status : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        referenceId = arrayOf(
            "1",
            "2",
            "3"
        )

        scheduleDate = arrayOf(
            "March 23",
            "September 6",
            "January 2"
        )

        scheduleTime = arrayOf(
            "11:11",
            "10:10",
            "9:09"
        )

        purpose = arrayOf(
            "Grades",
            "Card",
            "Id"
        )

        status = arrayOf(
            "ready",
            "secret",
            "not yet"
        )

        newRecyclerView=findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<DataOfAppointmentCard>()
        getUserData()
    }

    private fun getUserData() {
        for (i in referenceId.indices) {
            val dataOfAppointmentCard = DataOfAppointmentCard(
                referenceId[i],
                scheduleDate[i],
                scheduleTime[i],
                purpose[i],
                status[i]
            )
            newArrayList.add(dataOfAppointmentCard)
        }
        newRecyclerView.adapter = MyAdapter(newArrayList)
    }



}