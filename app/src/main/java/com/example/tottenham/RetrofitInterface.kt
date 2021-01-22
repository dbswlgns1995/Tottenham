package com.example.tottenham

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("player")
    fun requestAllData() : Call<ArrayList<Player>>

    @GET("player")
    fun getPlayerData(@Query("num") num : Int) : Call<ArrayList<Player>>

    @GET("daily")
    fun getDailyData(@Query("month") month : Int) : Call<ArrayList<Daily>>


}