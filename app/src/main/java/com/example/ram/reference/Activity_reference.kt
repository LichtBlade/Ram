package com.example.ram.reference

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.RequiresApi
import com.example.ram.ScheduleApiService
import com.example.ram.databinding.ActivityReferenceBinding
import com.example.ram.homepage.ActivityHome
import com.example.ram.reference.ScheduleRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class Activity_reference : AppCompatActivity() {
    private lateinit var binding: ActivityReferenceBinding
    private val CREATE_APPOINTMENT_REQUEST_CODE = 100

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val scheduleApiService = retrofit.create(ScheduleApiService::class.java)

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedPurposes = intent.getStringExtra("selectedPurposes") ?: ""
        val selectedDate = intent.getLongExtra("selectedDate", 0)
        val selectedStartTime = intent.getStringExtra("selectedStartTime") ?: ""
        val selectedEndTime = intent.getStringExtra("selectedEndTime") ?: ""
        val creator_id = intent.getStringExtra("creator_id")

        val startTime = selectedStartTime.replace("\\s*(am|pm)\\b".toRegex(RegexOption.IGNORE_CASE), "")
        val endTime = selectedEndTime.replace("\\s*(am|pm)\\b".toRegex(RegexOption.IGNORE_CASE), "").padStart(5, '0')

        Toast.makeText(this,"$endTime + $startTime",Toast.LENGTH_SHORT).show()

        val start = Random.nextInt(1, 101)
        val count = Random.nextInt(1, 11)
        val referenceIds = generateUniqueReferenceIds(start, count)
        val referenceId = referenceIds.first()

        binding.tvReferenceID.text = referenceId

        binding.btnHome.setOnClickListener {
            sendDataToBackend(creator_id, selectedDate, referenceId, startTime, endTime, selectedPurposes)
        }
    }

    private fun sendDataToBackend(creatorId: String?, selectedDate: Long, referenceId: String, selectedStartTime: Any, selectedEndTime: Any, selectedPurposes: String) {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(selectedDate)
        val requestBody = ScheduleRequest(creatorId, referenceId, formattedDate, selectedStartTime, selectedEndTime, selectedPurposes)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = scheduleApiService.sendScheduleData(requestBody)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Activity_reference, "SHEESHHHH", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(this@Activity_reference, ActivityHome::class.java))
                    val intent = Intent(this@Activity_reference, ActivityHome::class.java)
                    intent.putExtra("creator_id", creatorId)
                    startActivity(intent)
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Activity_reference, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.printStackTrace()
                    Toast.makeText(this@Activity_reference, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun generateUniqueReferenceIds(start: Int, count: Int): Array<String> {
        val referenceIds = mutableListOf<String>()
        for (i in start until start + count) {
            val uniqueIdentifier = UUID.randomUUID().toString().substring(0, 4)
            referenceIds.add("RAM$i$uniqueIdentifier")
        }
        return referenceIds.toTypedArray()
    }
}
