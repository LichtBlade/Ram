package com.example.ram


import com.example.ram.homepage.AppointmentResponse
import com.example.ram.homepage.update.Schedule
import com.example.ram.reference.ScheduleRequest

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ScheduleApiService {
    @GET("fetch_sched_user.php")
    suspend fun fetchSchedules(@Query("creator_id") creatorId: String): List<Schedule>


    @POST("schedules/create")
    suspend fun sendScheduleData(@Body request: ScheduleRequest): ResponseBody


    @GET("appointment/created")
    fun getAppointmentsForCreatorId(@Query("creator_id") creatorId: String): Call<List<AppointmentResponse>>

}
