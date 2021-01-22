package com.example.tottenham


import com.google.gson.annotations.SerializedName

data class Player(
    val birth: String,
    val height: String,
    val image: String,
    val name: String,
    val nation: String,
    val num: Int,
    val position: String
)