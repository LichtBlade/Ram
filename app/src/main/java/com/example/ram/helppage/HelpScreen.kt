package com.example.ram.helppage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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

        val btnReturn = findViewById<Button>(R.id.btn_return)
        btnReturn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun addDataToList() {
        mList.add(
            DataOfHelpCard(
                "How do I login?",
                "You can login using your Student ID Number. You can get your Password from the ITS office inside the campus."
            )
        )


        mList.add(
            DataOfHelpCard(
                "What documents or information should i bring to my appointment?",
                "When creating an appointment details such as required documents and fees will be displayed. Every school Documents have different requirements."
            )
        )

        mList.add(
            DataOfHelpCard(
                "Can i reschedule or cancel my appointment?",
                "Yes, You can edit or reschedule and delete appointments inside the Home Page where your appointments are displayed."
            )
        )

        mList.add(
            DataOfHelpCard(
                "What is the policy for missed or late appointments?",
                "Your appointment will be marked as 'No show' if you miss an appointment or if you are late for your appointment."
            )
        )

        mList.add(
            DataOfHelpCard(
                "Is there a limit to the number of appointments i can schedule?",
                "There is no limited number of appointments. You can book multiple appointments with different purposes."
            )
        )

        mList.add(
            DataOfHelpCard(
                "Can a representative attend to my appointment?",
                "Yes! You can set an appointment for them, Just make sure to provide an authorization letter during appointment period."
            )
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}