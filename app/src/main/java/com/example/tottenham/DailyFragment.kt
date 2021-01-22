package com.example.tottenham

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tottenham.R
import kotlinx.android.synthetic.main.fragment_daily.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class DailyFragment : Fragment(), View.OnClickListener {

    var month : Int = 1 // 변경 가능 날짜
    val cal = Calendar.getInstance()
    val current_month : Int = (cal.get(Calendar.MONTH)+1) // 현재 월 구하기
    val TAG : String = "***일정프래그먼트"

    var dailyData : ArrayList<Daily> = ArrayList()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_daily, container, false)

        setViews(root)
        setView(month)

        return root
    }

    fun setViews(root : View){
        month = current_month

        Log.d(TAG, "이번 달 : ${month}")
        val left_btn : Button = root.findViewById(R.id.daily_left_btn)
        val right_Btn : Button = root.findViewById(R.id.daily_right_btn)
        val month_text : TextView = root.findViewById(R.id.daily_month_text)

        left_btn.setOnClickListener(this)
        right_Btn.setOnClickListener(this)
        month_text.text = resources.getStringArray(R.array.month).get(month-1)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.daily_left_btn->{  // -1월, textview 변경, framelayout 변경
                if (month > 1){ // 1월이면 event 없음
                    month = month - 1
                    Log.d(TAG, "${month}")
                    daily_month_text.text = resources.getStringArray(R.array.month).get(month-1)
                    setView(month)
                }
            }

            R.id.daily_right_btn->{ // +1월, textview 변경, framelayout 변경

                if (month < 12){ // 12월이면 event 없음
                    month = month + 1
                    Log.d(TAG, "${month}")
                    daily_month_text.text = resources.getStringArray(R.array.month).get(month-1)
                    setView(month)
                }

            }
        }

    }

    private fun setAdapter(dailyList : ArrayList<Daily>){
        val dailyAdapter = DailyRecyclerviewAdapter(dailyList)
        daily_recyclerview.adapter = dailyAdapter
        daily_recyclerview.layoutManager = LinearLayoutManager(activity)
    }


    private fun setView(month : Int){

        val retrofit = Retrofit.Builder()
            .baseUrl("http://119.196.24.232:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(RetrofitInterface::class.java)
        retrofitService.getDailyData(month).enqueue(object : Callback<ArrayList<Daily>>{

            override fun onResponse(
                call: Call<ArrayList<Daily>>,
                response: Response<ArrayList<Daily>>
            ) {
                if(response.isSuccessful){
                    Log.d(TAG, "불러옴")
                    dailyData = response.body()!!
                    if(dailyData.size == 0){
                        Log.d(TAG, "없당")
                        Toast.makeText(context, "해당 경기가 없습니다!", Toast.LENGTH_SHORT).show()
                        daily_recyclerview.adapter = null
                    }else{
                        Log.d(TAG, "있당")
                        setAdapter(dailyData)
                    }


                }else{
                    Log.d(TAG, "불러오긴했는데")

                }
            }

            override fun onFailure(call: Call<ArrayList<Daily>>, t: Throwable) {
                Log.d(TAG, "실패")
                Toast.makeText(context, "서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })
    }

}