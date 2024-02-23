package com.example.ram.homepage

import com.google.gson.annotations.SerializedName

class CreateAppointment (
    @SerializedName("creator_id") val creator_id: String,
    @SerializedName("reference_id") val referenceId: String,
    @SerializedName("scheduled_date") val  schedule_date: String,
    @SerializedName("start_time") val schedule_Time: String,
    @SerializedName("end_time") val end_time: String,
    @SerializedName("purpose") val api_purpose: String,
    @SerializedName("status") val status: String
)