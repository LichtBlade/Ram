package com.example.ram.login

import com.google.gson.annotations.SerializedName

class LoginResponse(
    @SerializedName("user_id") val userId: String,
    @SerializedName("isLoggedIn") val isLoggedIn: Boolean,
    @SerializedName("loginTime") val loginTime: String
)