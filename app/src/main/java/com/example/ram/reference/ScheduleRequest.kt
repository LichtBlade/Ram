package com.example.ram.reference

import com.google.gson.annotations.SerializedName

data class ScheduleRequest(
    @SerializedName("creator_id")
    val creatorId: String?,
    @SerializedName("reference_id")
    val referenceId: String,
    @SerializedName("scheduled_date")
    val scheduledDate: String,
    @SerializedName("start_time")
    val startTime: Any,
    @SerializedName("end_time")
    val endTime: Any,
    @SerializedName("purpose")
    val purpose: String

)
