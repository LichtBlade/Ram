package com.example.ram

import com.example.ram.details.DetailsClass
import com.example.ram.homepage.AppointmentResponse
import com.example.ram.homepage.CreateAppointment
import com.example.ram.homepage.update.UpdateSchedule
import com.example.ram.login.LoginResponse
import com.example.ram.token.CSRFTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("user_id") userId: String,
        @Field("password") password: String
    ): Response<LoginResponse>



    // For Creating Schedules
    @FormUrlEncoded
    @POST("schedules/create")
    suspend fun createAppointment(
        @Field("creator_id") CreatorId: String,
        @Field("reference_id")  referenceId: String,
        @Field("scheduled_date")    schedule_date: String,
        @Field("start_time")    schedule_Time: String,
        @Field("end_time")      end_time: String,
        @Field("purpose")   purpose: String,
        @Field("status")    status: String
    ): Response<CreateAppointment>

    // FOR FETCHING NAME AND EMAILS

    @GET("users/{user_id}/info")
    fun fetchUserDetails(
        @Query("user_id") userId: String
    ): Call<DetailsClass>


    @GET("appointment/created")
    fun getAppointmentsForCreatorId(
        @Query("creator_id") creatorId: String
    ): Call<AppointmentResponse>



    // FOR DETAILS POST
    @FormUrlEncoded
    @POST("schedules/create")
    suspend fun sendDataToBackend(
        @Field("creator_id") creatorId: String?,
        @Field("scheduled_date") formattedDate: String,
        @Field("start_time") selectedStartTime: Any,
        @Field("end_time") selectedEndTime: Any,
        @Field("purpose") selectedPurposes: String
    ): Response<String>

    // FOR DELETING APPOINTMENT
    @FormUrlEncoded
    @POST("schedules/delete")
    fun deleteSchedule(
        @Field("reference_id") referenceId: String
    ): Call<Void>



    // TOKEN
    @GET("csrf-token")
    fun getCSRFToken(): Call<CSRFTokenResponse>

    @POST("login")
    fun loginWithCsrfToken(
        @Query("user_id") userId: String,
        @Query("password") password: String,
        @Query("_token") csrfToken: String
    ): Call<LoginResponse>

    // Update Schedule
    @PUT("appointments/{reference_id}")
    fun rescheduleAppointment(
        @Path("reference_id") referenceId: String,
        @Body appointment: UpdateSchedule
    ): Call<Void>
}