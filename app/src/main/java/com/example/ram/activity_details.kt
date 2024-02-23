package com.example.ram

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ram.databinding.ActivityDetailsBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale

class activity_details : AppCompatActivity() {
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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //RETRIEVE CREATOR ID
        val creatorId = intent.getStringExtra("creator_id")

        //RETRIEVE THE PURPOSE, DATE, AND TIME
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








    }


}

// Read the
