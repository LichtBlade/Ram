package com.example.ram.appointment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ram.R
import com.example.ram.activity_schedule
import com.example.ram.databinding.ActivityAppointmentPurposeBinding


class AppointmentPurpose : AppCompatActivity() {
    private lateinit var binding: ActivityAppointmentPurposeBinding

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<DataOfPurposeCard>

    private val selectedPurposeDetails = mutableListOf<DataOfPurposeCard>() // Define selectedPurposeDetails here

    lateinit var purposeAppointment: Array<String>
    lateinit var requirements: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentPurposeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val creatorId = intent.getStringExtra("creator_id")

        purposeAppointment = arrayOf(
            "Official Transcript\n" +
                    "of Records",
            "Transfer Credentials",
            "Certifications",
            "Diploma",
            "Authentication of\n" +
                    "Records",
            "International Documents",
            "New Student"
        )


        requirements = arrayOf(
            "User: Student/Guardian\n" +
                    "Requirements: Filled-up request form with stub\n" +
                    "Payment: To be paid in finance\n" +
                    "₱180 per page (if succeeding)\n" +
                    "₱1500 (2018 alumni and below)\n" +
                    "₱35 fresh graduate",

            "User : Student/Guardian\n" +
                    "- Requirements to collect copy of OTR: filled-up request form with stab\n" +
                    "- Payment: to be paid in finance\n" +
                    "• P575 + 35 pesos for stamp (College)\n" +
                    "• P595 or 610 (Post-grad)\n",

            "User: Students/Guardian\n" +
                    "- Requirements to collect: filled-up request form with stab\n" +
                    "- Payment: P35 to be paid in registrar\n" +
                    "(3 working days)\n",

            "User : Student/Guardian\n" +
                    "- Requirements to collect: filled-up request form with stab\n" +
                    "- Payment: to be paid in finance\n" +
                    "• Free (if fresh graduate)\n" +
                    "• P615 (if fresh graduate tapos nawala)\n" +
                    "• P 2000 (if alumni)\n" +
                    "(10 working days)\n",

            "User : Student/Guardian\n" +
                    "- Requirements to collect: filled-up request form with stab\n" +
                    "- Payment: to be paid in finance\n" +
                    "• P170 (If local)\n" +
                    "• P200 (abroad)\n",

            " User : Student/Guardian\n" +
                    "- Requirements to collect: filled-up request form with stab\n" +
                    "- Payment: P100 to be paid in finance\n",

            "User : Student/Guardian\n" +
                    "- Requirements to collect:\n" +
                    "• Form 137 (senior high)\n" +
                    "• Form 138\n" +
                    "• Certificate of completion\n" +
                    "• PSA\n" +
                    "- Payment: no payment\n"
        )

        newRecyclerView = findViewById(R.id.recyclerview_purpose)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<DataOfPurposeCard>()

        // Call getUserData() to populate the RecyclerView with data
        getUserData()

        binding.btnNext.setOnClickListener {
            if (selectedPurposeDetails.isEmpty()) {
                Toast.makeText(this, "You need to select at least one purpose.", Toast.LENGTH_SHORT).show()
            } else {
                val purposesString = selectedPurposeDetails.joinToString(", ") { it.purpose.toString() }
                val requirementsString = selectedPurposeDetails.joinToString(", ") { it.requirements.toString() }
//                val paymentInfoString = selectedPurposeDetails.joinToString(", ") { it.paymentInfo }.toString()

                val intent = Intent(this, activity_schedule::class.java)
                intent.putExtra("selectedPurposes", purposesString)
                intent.putExtra("requirements", requirementsString)
//                intent.putExtra("paymentInfo", paymentInfoString)
                intent.putExtra("creator_id", creatorId)
                startActivity(intent)
            }
        }
    }

    // In AppointmentPurpose class
    private fun getUserData() {
        for (i in purposeAppointment.indices) {
            val dataOfPurposeCard = DataOfPurposeCard(
                purposeAppointment[i],
                false, // Initialize isSelected to false
                requirements[i],
            )
            newArrayList.add(dataOfPurposeCard)
        }
        newRecyclerView.adapter = PurposeAdapter(newArrayList) { selectedPurpose ->
            // Handle purpose selection here if needed
            // For example, you can show a Toast message with the selected purpose
            selectedPurposeDetails.clear()
            selectedPurposeDetails.addAll(newArrayList.filter { it.isSelected })
        }

    //return button
        binding.btnReturn.setOnClickListener{
            onBackPressed()
        }


    }
}

