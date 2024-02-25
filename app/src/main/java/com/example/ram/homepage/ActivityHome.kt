package com.example.ram.homepage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ram.ApiService
import com.example.ram.R
import com.example.ram.appointment.AppointmentPurpose
import com.example.ram.appointment.DataOfAppointmentCard
import com.example.ram.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActivityHome : AppCompatActivity() {
    // For Binding
    private lateinit var binding: ActivityHomeBinding

    // For RecyclerView
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<DataOfAppointmentCard>
    private lateinit var myAdapter: MyAdapter

    // For drawer navigation
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
            intent.putExtra("creator_id", creatorId)
            startActivityForResult(intent, CREATE_APPOINTMENT_REQUEST_CODE)
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

        // Initialize RecyclerView and Adapter
        newRecyclerView = findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newArrayList = ArrayList()
        myAdapter = MyAdapter(newArrayList)
        newRecyclerView.adapter = myAdapter

        // Fetch data from API
        if(creatorId != null) {
            fetchDataFromAPI(creatorId)
            fetchUserDetails(creatorId,this@ActivityHome)
        }
    }

    private fun fetchDataFromAPI(creatorId: String?) {
        if (creatorId.isNullOrEmpty()) {
            Log.e("API Error", "Creator ID is null or empty")
            return
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("http://64.23.183.4/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getAppointmentsForCreatorId(creatorId)

        call.enqueue(object : Callback<AppointmentResponse> {
            override fun onResponse(call: Call<AppointmentResponse>, response: Response<AppointmentResponse>) {
                if (response.isSuccessful) {
                    val appointmentsResponse = response.body()
                    appointmentsResponse?.let {
                        val appointments = it.appointments
                        // Update UI with fetched appointments data
                        updateRecyclerView(appointments)
                    }
                } else {
                    // Handle unsuccessful response
                    Log.e("API Error", "Failed to fetch appointments: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AppointmentResponse>, t: Throwable) {
                // Handle failure
                Log.e("API Error", "Failed to fetch appointments", t)
            }
        })
    }

    // Function to update RecyclerView with new data
    private fun updateRecyclerView(appointments: List<Appointment>) {
        newArrayList.clear() // Clear existing data
        if (appointments.isNotEmpty()) {
            newArrayList.addAll(appointments.map { appointment ->
                DataOfAppointmentCard(
                    appointment.referenceId,
                    appointment.scheduledDate,
                    "${appointment.startTime}",
                    appointment.purpose,
                    appointment.status
                )
            })
            myAdapter.notifyDataSetChanged() // Notify adapter of dataset change
        } else {
            // Handle case where appointments list is empty
            // For example, show a message indicating no appointments
        }
    }

    // Override onActivityResult to handle result from appointment creation
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_APPOINTMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            // Appointment created successfully, fetch updated data
            val creatorId = data?.getStringExtra("creator_id")
            if (creatorId != null) {
                fetchDataFromAPI(creatorId)
            }
        }
    }






    override fun onBackPressed() {
        // Check if the drawer is open, if so, close it on back press
        if (Drawer_Layout.isDrawerOpen(GravityCompat.START)) {
            Drawer_Layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val CREATE_APPOINTMENT_REQUEST_CODE = 100
    }



    @SuppressLint("SetTextI18n")
    private fun fetchUserDetails(userId: String, context: Context) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://64.23.183.4/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService: ApiService = retrofit.create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.fetchUserDetails(userId).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val navEmailTextView = Navigation_View.findViewById<TextView>(R.id.nav_email)
                        val navFullName = Navigation_View.findViewById<TextView>(R.id.nav_name)

                        val userDetails = response.body()
                        userDetails?.let {
                            // Process the user details
                            val firstName = it.firstName
                            val lastName = it.lastName
                            val emailAddress = it.emailAddress
                            navEmailTextView.text = emailAddress
                            navFullName.text = "$firstName $lastName"
                        }
                    } else {
                        Toast.makeText(context, "Failed to fetch user details", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("MainActivity", "Error: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }




}
