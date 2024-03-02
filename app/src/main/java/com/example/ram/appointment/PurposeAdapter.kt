package com.example.ram.appointment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
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
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)
        val arrow: TextView = itemView.findViewById(R.id.textView_dropdown)
        val card: CardView = itemView.findViewById(R.id.cv_purpose)

        init {
            checkBox.setOnClickListener {
                val data = purpose[adapterPosition]
                if (checkBox.isChecked) {
                    if (selectedCount < 2) {
                        selectedCount++
                        data.isSelected = true
                        onPurposeSelectedListener.invoke(data.purpose ?: "")
                    } else {
                        checkBox.isChecked = false
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
            checkBox.isChecked = data.isSelected
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
        val currentItem = purpose[position]
        holder.bind(purpose[position])
        holder.arrow.text = currentItem.arrowSign

        val isExpandable: Boolean = purpose[position].isExpandable
        holder.requirement.visibility = if (isExpandable) View.VISIBLE else View.GONE

        if (!isExpandable){
            holder.arrow.text = "▼"
            holder.arrow.setTextColor(Color.parseColor("#3A3A3A"))
            holder.purposeOfVisit.setTextColor(Color.parseColor("#3A3A3A"))
            holder.requirement.setTextColor(Color.parseColor("#3A3A3A"))
            holder.card.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }else{
            holder.arrow.text = "▲"
            holder.arrow.setTextColor(Color.parseColor("#FFFFFF"))
            holder.purposeOfVisit.setTextColor(Color.parseColor("#FFFFFF"))
            holder.requirement.setTextColor(Color.parseColor("#FFFFFF"))
            holder.card.setCardBackgroundColor(Color.parseColor("#5D8E7D"))
        }

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
