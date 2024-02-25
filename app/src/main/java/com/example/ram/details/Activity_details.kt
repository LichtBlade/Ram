package com.example.ram.details

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ram.ApiService
import com.example.ram.databinding.ActivityDetailsBinding
import com.example.ram.reference.Activity_reference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Activity_details : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var confirmAppointmentButton: Button
    private lateinit var selectedPurposes: String
    private lateinit var requirements: String
    private lateinit var paymentInfo: String

    private var selectedDate: Long = 0

    private var selectedStartTime: String = ""
    private var selectedEndTime: String = ""

    private lateinit var purposeTextView: TextView
    private lateinit var reqTextView: TextView
    private lateinit var paymentTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var timeTextView: TextView
    val BASE_URL = "http://10.0.2.2:8000/api/"
    fun getColoredSpannable(text: String, color: Int): SpannableString {
        val spannable = SpannableString(text)
        spannable.setSpan(
            ForegroundColorSpan(color),
            0,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
//return button
        binding.btnReturn.setOnClickListener{
            onBackPressed()
        }
        // Retrieve creator ID
        val creatorId = intent.getStringExtra("creator_id")

        // Retrieve the purpose, date, and time
        selectedPurposes = intent.getStringExtra("selectedPurposes") ?: ""
        selectedDate = intent.getLongExtra("selectedDate", 0)
        selectedStartTime = intent.getStringExtra("selectedStartTime") ?: ""
        selectedEndTime = intent.getStringExtra("selectedEndTime") ?: ""
        requirements = intent.getStringExtra("requirements") ?: ""
        paymentInfo = intent.getStringExtra("paymentInfo") ?: ""

        // Assign TextViews from binding
        purposeTextView = binding.tvUserpurpose
        dateTextView = binding.tvUserdate
        timeTextView = binding.tvUsertime
        reqTextView = binding.tvUserrequirements
        paymentTextView = binding.tvUserothers

        // Set text to TextViews
        purposeTextView.text = selectedPurposes
        dateTextView.text = selectedDate.toString()
        timeTextView.text = "$selectedStartTime - $selectedEndTime"
        reqTextView.text = requirements
        paymentTextView.text = paymentInfo

        fetchUserDetails(creatorId.toString(), this)

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, Activity_reference::class.java)
            intent.putExtra("selectedPurposes", selectedPurposes)
            intent.putExtra("requirements", requirements)
            intent.putExtra("selectedDate", selectedDate)
            intent.putExtra("selectedStartTime", selectedStartTime)
            intent.putExtra("selectedEndTime", selectedEndTime)
            intent.putExtra("creator_id", creatorId)
            startActivity(intent)
        }
    }



    @SuppressLint("SetTextI18n")
    private fun fetchUserDetails(userId: String, context: Context) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService: ApiService = retrofit.create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.fetchUserDetails(userId).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val userDetails = response.body()
                        userDetails?.let {
                            // Process the user details
                            val firstName = it.firstName
                            val lastName = it.lastName
                            val emailAddress = it.emailAddress
                            // Update UI with user details
                            binding.tvUseremail.text = emailAddress
                            binding.tvUsername.text = "$firstName , $lastName"
                        }
                    } else {
                        Toast.makeText(context, "Failed to fetch user details", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("MainActivity", "Error: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }






}







