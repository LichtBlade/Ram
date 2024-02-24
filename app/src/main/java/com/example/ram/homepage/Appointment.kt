package com.example.ram.homepage

import com.google.gson.annotations.SerializedName

data class Appointment(
    @SerializedName("reference_id") val referenceId: String,
    @SerializedName("scheduled_date") val scheduledDate: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("purpose") val purpose: String,
    @SerializedName("status") val status: String
)