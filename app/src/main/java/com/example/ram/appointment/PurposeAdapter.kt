package com.example.ram.appointment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.ram.R
import com.example.ram.helppage.DataOfHelpCard

class PurposeAdapter(private var purpose: List<DataOfPurposeCard>) : RecyclerView.Adapter<PurposeAdapter.PurposeViewHolder>(){

    inner class PurposeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val purposeOfVisit: TextView = itemView.findViewById(R.id.tv_Purpose)
        val requirement: TextView = itemView.findViewById(R.id.tv_requirements)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)

        fun collapseExpandedView(){
            requirement.visibility = View.GONE
        }
    }

    fun setFilteredList(mList: List<DataOfPurposeCard>) {
        this.purpose = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurposeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.helpcard, parent, false)
        return PurposeViewHolder(view)
    }

    override fun onBindViewHolder(holder: PurposeViewHolder, position: Int) {

        val currentItem = purpose[position]
        holder.purposeOfVisit.text = currentItem.purpose
        holder.requirement.text = currentItem.requirements

        val isExpandable: Boolean = currentItem.isExpandable
        holder.requirement.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.constraintLayout.setOnClickListener {
            isAnyItemExpanded(position)
            currentItem.isExpandable = !currentItem.isExpandable
            notifyItemChanged(position , Unit)
        }

    }

    private fun isAnyItemExpanded(position: Int){
        val temp = purpose.indexOfFirst {
            it.isExpandable
        }
        if (temp >= 0 && temp != position){
            purpose[temp].isExpandable = false
            notifyItemChanged(temp , 0)
        }
    }

    override fun onBindViewHolder(
        holder: PurposeViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        if(payloads.isNotEmpty() && payloads[0] == 0){
            holder.collapseExpandedView()
        }else{
            super.onBindViewHolder(holder, position, payloads)

        }
    }

    override fun getItemCount(): Int {
        return purpose.size
    }
}