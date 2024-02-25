package com.example.ram.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.*
import androidx.recyclerview.widget.RecyclerView
import com.example.ram.ApiService
import com.example.ram.GlobalVariables
import com.example.ram.R
import com.example.ram.appointment.DataOfAppointmentCard
import com.example.ram.details.RetrofitClient.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyAdapter(private val appointmentList: ArrayList<DataOfAppointmentCard>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.appointmentcard,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return appointmentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = appointmentList[position]
        holder.referenceId.text = currentItem.referenceId.toString()
        holder.scheduleDate.text = currentItem.scheduleDate
        holder.scheduleTime.text = currentItem.scheduleTime
        holder.purpose.text = currentItem.purpose
        holder.status.text = currentItem.status

        holder.deleteButton.setOnClickListener{
            val referenceId = currentItem.referenceId // Get the reference ID of the appointment to delete
            deleteAppointment(referenceId.toString(), position)
        }

    }

    private fun deleteAppointment(referenceId: String, position: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val requestCall: Call<Void> = apiService.deleteSchedule(referenceId)

        requestCall.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Appointment deleted successfully, remove from the list and update UI
                    appointmentList.removeAt(position)
                    notifyDataSetChanged()
                } else {
                    // Handle unsuccessful deletion response
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle failure here
                t.printStackTrace()
            }
        })
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val referenceId: TextView = itemView.findViewById(R.id.tvMyRefID)
        val scheduleDate: TextView = itemView.findViewById(R.id.tvMyDate)
        val scheduleTime: TextView = itemView.findViewById(R.id.tvMyTime)
        val purpose: TextView = itemView.findViewById(R.id.tvMyPurpose)
        val status: TextView = itemView.findViewById(R.id.tvMyStatus)

        // Initialize ImageButton views
        val editButton: ImageButton = itemView.findViewById(R.id.imgEdit)
        val deleteButton: ImageButton = itemView.findViewById(R.id.imgDelete)
    }
}