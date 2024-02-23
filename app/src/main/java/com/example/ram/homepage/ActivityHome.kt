package com.example.ram.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ram.R
import com.example.ram.ScheduleApiService
import com.example.ram.appointment.AppointmentPurpose
import com.example.ram.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    //drawer nav
    private lateinit var ImageUser: ImageView
    private lateinit var Navigation_View: NavigationView
    private lateinit var Drawer_Layout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ImageUser = findViewById(R.id.imgUser)
        Navigation_View = findViewById(R.id.navigation_view)
        Drawer_Layout = findViewById(R.id.drawer_layout)

        ImageUser.setOnClickListener {
            Drawer_Layout.openDrawer(Navigation_View)
        }


        val creatorId = intent.getStringExtra("creator_id")


        binding.btnCreateAppointment.setOnClickListener {
            val intent = Intent(this, AppointmentPurpose::class.java)
            Toast.makeText(this,"$creatorId",Toast.LENGTH_SHORT).show()
            intent.putExtra("creator_id", creatorId)
            startActivity(intent)
        }







        Drawer_Layout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                // Do something when drawer is opened
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                // Do something when drawer is closed
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                // Do something while drawer is sliding
            }

            override fun onDrawerStateChanged(newState: Int) {
                super.onDrawerStateChanged(newState)
                // Do something when drawer state changes
            }
        })

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

    // Set a listener for the DrawerLayout to close the drawer when tapped outside
    override fun onBackPressed() {
        // Check if the drawer is open, if so, close it on back press
        if (Drawer_Layout.isDrawerOpen(GravityCompat.START)) {
            Drawer_Layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
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


    // FETCHING

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://rammanager-001-site1.btempurl.com/RAM/Homepage/assets/script/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val scheduleApiService = retrofit.create(ScheduleApiService::class.java)








}