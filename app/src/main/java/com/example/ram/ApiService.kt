package com.example.ram

import com.example.ram.homepage.CreateAppointment
import com.example.ram.login.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.Response

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("user_id") userId: String,
        @Field("password") password: String
    ): Response<LoginResponse>


    // For Creating Schedules
    @FormUrlEncoded
    @POST("createSchedule")
    suspend fun createAppointment(
        @Field("creator_id") CreatorId: String,
        @Field("reference_id")  referenceId: String,
        @Field("scheduled_date")    schedule_date: String,
        @Field("start_time")    schedule_Time: String,
        @Field("end_time")      end_time: String,
        @Field("purpose")   purpose: String,
        @Field("status")    status: String
    ): Response<CreateAppointment>


}