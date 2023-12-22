package com.example.ram.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ram.R

class MyAdapter(private val appointmentList : ArrayList<DataOfAppointmentCard>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.appointmentcard,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return appointmentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = appointmentList[position]
        holder.referenceId.text = currentItem.referenceId
        holder.scheduleDate.text = currentItem.scheduleDate
        holder.scheduleTime.text = currentItem.scheduleTime
        holder.purpose.text = currentItem.purpose
        holder.status.text = currentItem.status
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val referenceId : TextView = itemView.findViewById(R.id.tvMyRefID)
        val scheduleDate : TextView = itemView.findViewById(R.id.tvMyDate)
        val scheduleTime : TextView = itemView.findViewById(R.id.tvMyTime)
        val purpose : TextView = itemView.findViewById(R.id.tvMyPurpose)
        val status : TextView = itemView.findViewById(R.id.tvMyStatus)


    }
}