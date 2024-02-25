package com.example.ram.homepage

import com.google.gson.annotations.SerializedName

data class AppointmentResponse(
    @SerializedName("message") val message: String,
    @SerializedName("appointments") val appointments: List<Appointment>
)