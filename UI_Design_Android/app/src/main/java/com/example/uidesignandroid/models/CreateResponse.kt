package com.example.uidesignandroid.models


import com.google.gson.annotations.SerializedName

data class CreateResponse(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("job")
    val job: String,
    @SerializedName("name")
    val name: String
)