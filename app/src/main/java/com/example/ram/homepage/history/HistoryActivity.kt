package com.example.ram.homepage.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ram.ApiService
import com.example.ram.R
import com.example.ram.appointment.DataOfAppointmentCard
import com.example.ram.databinding.ActivityHistoryBinding
import com.example.ram.homepage.Appointment
import com.example.ram.homepage.AppointmentResponse
import com.example.ram.homepage.MyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    // For RecyclerView
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<DataOfAppointmentCard>
    private lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val creatorId = intent.getStringExtra("creator_id")




        // Initialize RecyclerView and Adapter
        newRecyclerView = findViewById(R.id.recycler_history)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newArrayList = ArrayList()
        myAdapter = MyAdapter(newArrayList, this)
        newRecyclerView.adapter = myAdapter

        // Fetch data from API
        if (creatorId != null) {
            fetchDataFromAPI(creatorId)
        }
    }

    private fun updateRecyclerView(appointments: List<Appointment>) {
        newArrayList.clear() // Clear existing data

        val filteredAppointments = appointments.filter { it.status.equals("DONE", ignoreCase = true)|| it.status.equals("CANCELLED", ignoreCase = true) }

        if (filteredAppointments.isNotEmpty()) {
            newArrayList.addAll(filteredAppointments.map { appointment ->
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
            // Handle case where filtered appointments list is empty
            // For example, show a message indicating no appointments
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
            override fun onResponse(
                call: Call<AppointmentResponse>,
                response: Response<AppointmentResponse>
            ) {
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
    companion object {
        private const val CREATE_APPOINTMENT_REQUEST_CODE = 100
    }
}