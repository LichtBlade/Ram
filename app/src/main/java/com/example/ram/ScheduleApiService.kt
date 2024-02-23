package com.example.ram

import com.example.ram.homepage.Schedule
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApiService {
    @GET("fetch_sched_user.php")
    suspend fun fetchSchedules(@Query("creator_id") creatorId: String): List<Schedule>
}
