package com.example.ram

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

}