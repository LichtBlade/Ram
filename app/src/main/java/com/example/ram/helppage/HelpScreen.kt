package com.example.ram.helppage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ram.R
import com.example.searchviewkotlin.HelpAdapter

class HelpScreen : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<DataOfHelpCard>()
    private lateinit var adapter: HelpAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_screen)

        recyclerView = findViewById(R.id.recyclerview_help)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        addDataToList()
        adapter = HelpAdapter(mList)
        recyclerView.adapter = adapter
    }

    private fun addDataToList() {
        mList.add(
            DataOfHelpCard(
                "How do i login?",
                "Secret kaya mo na yan geng geng"
            )
        )
    }
}