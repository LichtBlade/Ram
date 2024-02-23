package com.example.ram.details
import com.google.gson.annotations.SerializedName

data class DetailsClass(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("email_address")
    val emailAddress: String
)
