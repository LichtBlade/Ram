package com.example.ram.homepage

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ram.ApiService
import com.example.ram.R
import com.example.ram.appointment.DataOfAppointmentCard
import com.example.ram.homepage.update.ActivityUpdate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyAdapter(
    private val appointmentList: ArrayList<DataOfAppointmentCard>,
    private val context: Context
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

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
        holder.bind(currentItem)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val referenceId: TextView = itemView.findViewById(R.id.tvMyRefID)
        private val scheduleDate: TextView = itemView.findViewById(R.id.tvMyDate)
        private val scheduleTime: TextView = itemView.findViewById(R.id.tvMyTime)
        private val purpose: TextView = itemView.findViewById(R.id.tvMyPurpose)
        private val status: TextView = itemView.findViewById(R.id.tvMyStatus)

        // Initialize ImageButton views
        private val editButton: ImageButton = itemView.findViewById(R.id.imgEdit)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.imgDelete)

        init {
            editButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val statusText = appointmentList[position].status
                    if (statusText != "approved") {
                        val intent = Intent(context, ActivityUpdate::class.java)
                        val referenceId = appointmentList[position].referenceId.toString()
                        intent.putExtra("reference_id", referenceId)
                        context.startActivity(intent)
                    } else{
                        Toast.makeText(context, "bla bla bla", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val statusText = appointmentList[position].status
                    if (statusText != "approved") {
                        val referenceId = appointmentList[position].referenceId.toString()
                        showDeleteConfirmationDialog(referenceId, position)
                    }else{
                        Toast.makeText(context, "Don't bla bla", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // hide buttons
        fun bind(appointment: DataOfAppointmentCard) {
            referenceId.text = appointment.referenceId.toString()
            scheduleDate.text = appointment.scheduleDate
            scheduleTime.text = appointment.scheduleTime
            purpose.text = appointment.purpose
            status.text = appointment.status

            if (appointment.status == "done" || appointment.status == "cancelled") {
                editButton.visibility = View.INVISIBLE
                deleteButton.visibility = View.INVISIBLE
            }
        }

        private fun showDeleteConfirmationDialog(referenceId: String, position: Int) {
            AlertDialog.Builder(context)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton(android.R.string.yes) { dialog, which ->
                    deleteAppointment(referenceId, position)
                }
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }

        private fun deleteAppointment(referenceId: String, position: Int) {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://64.23.183.4/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)
            val requestCall: Call<Void> = apiService.deleteSchedule(referenceId)

            requestCall.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
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
    }
}
