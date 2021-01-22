package com.example.tottenham

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.playeritem.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class PlayerActivity : AppCompatActivity() {

    val TAG : String = "****PlayerActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        if(intent.hasExtra("num")){
            var num = intent.getIntExtra("num",0)
            Log.d(TAG, "백넘버 $num 선수")
            setView(num)
        }else{
            Log.d(TAG, "안왔쪙")
        }
    }

    private fun setView(num : Int){

        val retrofit = Retrofit.Builder()
            .baseUrl("http://119.196.24.232:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(RetrofitInterface::class.java)
        retrofitService.getPlayerData(num).enqueue(object  : Callback<ArrayList<Player>>{
            override fun onResponse(
                call: Call<ArrayList<Player>>,
                response: Response<ArrayList<Player>>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "불러옴")
                    var player : Player = response.body()?.get(0)!!
                    Glide.with(this@PlayerActivity).load(player.image).into(solo_imageview)
                    solo_num_text.text = "토트넘 홋스퍼 FC No. ${player.num}"
                    solo_name_text.text = player.name
                    solo_birth_text.text = player.birth
                    solo_height_text.text = player.height
                    solo_nation_text.text = player.nation
                    solo_position_text.text = player.position

                }else {
                    Log.d(TAG, "불러오긴했는데")
                }
            }

            override fun onFailure(call: Call<ArrayList<Player>>, t: Throwable) {
                Log.d(TAG, "실패")
                Toast.makeText(this@PlayerActivity, "서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })


    }
}
