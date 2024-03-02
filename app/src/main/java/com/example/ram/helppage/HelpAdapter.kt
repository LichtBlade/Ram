package com.example.searchviewkotlin

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.ram.R
import com.example.ram.helppage.DataOfHelpCard

class HelpAdapter(private var mList: List<DataOfHelpCard>) :
    RecyclerView.Adapter<HelpAdapter.LanguageViewHolder>() {

    inner class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textView_qA)
        val answer: TextView = itemView.findViewById(R.id.textView_answers)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)
        val arrow: TextView = itemView.findViewById(R.id.textView_dropdown)
        val card: CardView = itemView.findViewById(R.id.cv_question)

        fun collapseExpandedView(){
            answer.visibility = View.GONE
        }
    }

    fun setFilteredList(mList: List<DataOfHelpCard>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.helpcard, parent, false)
        return LanguageViewHolder(view)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {

        val currentItem = mList[position]
        holder.title.text = currentItem.tittle
        holder.answer.text = currentItem.answer
        holder.arrow.text = currentItem.arrowSign


        val isExpandable: Boolean = currentItem.isExpandable
        holder.answer.visibility = if (isExpandable){ View.VISIBLE  }else View.GONE

        if (!isExpandable){
            holder.arrow.text = "▼"
            holder.arrow.setTextColor(Color.parseColor("#3A3A3A"))
            holder.title.setTextColor(Color.parseColor("#3A3A3A"))
            holder.answer.setTextColor(Color.parseColor("#3A3A3A"))
            holder.card.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }else{
            holder.arrow.text = "▲"
            holder.arrow.setTextColor(Color.parseColor("#FFFFFF"))
            holder.title.setTextColor(Color.parseColor("#FFFFFF"))
            holder.answer.setTextColor(Color.parseColor("#FFFFFF"))
            holder.card.setCardBackgroundColor(Color.parseColor("#5D8E7D"))
        }



        holder.constraintLayout.setOnClickListener {
            isAnyItemExpanded(position)
            currentItem.isExpandable = !currentItem.isExpandable
            notifyItemChanged(position , Unit)
        }

    }

    private fun isAnyItemExpanded(position: Int){
        val temp = mList.indexOfFirst {
            it.isExpandable
        }
        if (temp >= 0 && temp != position){
            mList[temp].isExpandable = false
            notifyItemChanged(temp , 0)
        }
    }

    override fun onBindViewHolder(
        holder: LanguageViewHolder,
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
        return mList.size
    }
}