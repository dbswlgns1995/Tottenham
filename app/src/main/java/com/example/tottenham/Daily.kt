package com.example.tottenham


import com.google.gson.annotations.SerializedName

data class Daily(
    val day: String,
    val image: String,
    val league: String,
    val month: Int,
    val opponent: String,
    val score: String,
    val video: String
)