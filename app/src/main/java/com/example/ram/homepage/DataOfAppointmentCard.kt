package com.example.ram.appointment

data class DataOfAppointmentCard(
    val referenceId: Comparable<*>,
    val scheduleDate: String,
    val scheduleTime: String,
    val purpose: String,
    val status: String
)
