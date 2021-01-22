package com.example.tottenham

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_player.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class PlayerFragment : Fragment() {

    val TAG : String = "****PlayerFragment"
    var playerData : ArrayList<Player> = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_player, container, false)

        loadData()

        return root
    }


    private fun setAdapter(playerList : ArrayList<Player>){
        val playerAdapter = PlayerRecyclerviewAdapter(playerList)
        player_recyclerview.adapter = playerAdapter
        player_recyclerview.layoutManager = GridLayoutManager(activity,2)
    }

    private fun loadData(){

        val retrofit = Retrofit.Builder()
            .baseUrl("http://119.196.24.232:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(RetrofitInterface::class.java)
        retrofitService.requestAllData().enqueue(object : Callback<ArrayList<Player>>{
            override fun onResponse(
                call: Call<ArrayList<Player>>,
                response: Response<ArrayList<Player>>
            ) {
                if (response.isSuccessful) {
                    playerData = response.body()!!
                    setAdapter(playerData)
                    Log.d(TAG, "불러옴")
                }else {
                    Log.d(TAG, "불러오긴했는데")
                    Toast.makeText(context, "서버에서 해당 정보를 받아올 수 없습니다", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<Player>>, t: Throwable) {
                Toast.makeText(context, "서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })


    }

}
