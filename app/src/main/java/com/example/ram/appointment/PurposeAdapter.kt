package com.example.ram.appointment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.ram.R

class PurposeAdapter(
    private var purpose: List<DataOfPurposeCard>,
    private val onPurposeSelectedListener: (String) -> Unit
) : RecyclerView.Adapter<PurposeAdapter.PurposeViewHolder>() {

    private var selectedCount = 0

    inner class PurposeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val purposeOfVisit: TextView = itemView.findViewById(R.id.tv_purposecard)
        val requirement: TextView = itemView.findViewById(R.id.tv_requirements)
        val radioButton: RadioButton = itemView.findViewById(R.id.radioButton)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)

        init {
            radioButton.setOnClickListener {
                val data = purpose[adapterPosition]
                if (radioButton.isChecked) {
                    if (selectedCount < 2) {
                        selectedCount++
                        data.isSelected = true
                        onPurposeSelectedListener.invoke(data.purpose ?: "")
                    } else {
                        radioButton.isChecked = false
                        Toast.makeText(
                            itemView.context,
                            "You can only select up to 2 purposes",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // When the radio button is unchecked, set its isSelected property to false
                    data.isSelected = false
                    // Decrement the selectedCount
                    selectedCount--
                }
            }
        }


        fun bind(data: DataOfPurposeCard) {
            purposeOfVisit.text = data.purpose
            requirement.text = data.requirements
            radioButton.isChecked = data.isSelected
        }

        fun collapseExpandedView() {
            requirement.visibility = View.GONE
        }
    }

    fun setFilteredList(mList: List<DataOfPurposeCard>) {
        this.purpose = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurposeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.purposecard, parent, false)
        return PurposeViewHolder(view)
    }

    override fun onBindViewHolder(holder: PurposeViewHolder, position: Int) {
        holder.bind(purpose[position])

        val isExpandable: Boolean = purpose[position].isExpandable
        holder.requirement.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.constraintLayout.setOnClickListener {
            isAnyItemExpanded(position)
            purpose[position].isExpandable = !isExpandable
            notifyItemChanged(position, Unit)
        }
    }

    private fun isAnyItemExpanded(position: Int) {
        val temp = purpose.indexOfFirst { it.isExpandable }
        if (temp >= 0 && temp != position) {
            purpose[temp].isExpandable = false
            notifyItemChanged(temp, 0)
        }
    }

    override fun onBindViewHolder(
        holder: PurposeViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty() && payloads[0] == 0) {
            holder.collapseExpandedView()
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount(): Int {
        return purpose.size
    }
}
