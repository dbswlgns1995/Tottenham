package com.example.tottenham

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dailyitem.view.*
import java.util.zip.Inflater

class DailyRecyclerviewAdapter(val dailyData : ArrayList<Daily>) : RecyclerView.Adapter<DailyRecyclerviewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyRecyclerviewAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.dailyitem, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dailyData.size
    }

    override fun onBindViewHolder(holder: DailyRecyclerviewAdapter.ViewHolder, position: Int) {
        val daily = dailyData.get(position)
        holder.setView(daily)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setView(daily: Daily){
            Glide.with(itemView.context).load(daily.image).into(itemView.dailyitem_imageview)
            itemView.dailyitem_day_text.text = "날짜 : ${daily.day}"
            itemView.dailyitem_league_text.text = daily.league
            itemView.dailyitem_score_text.text = "토트넘 vs ${daily.opponent}  (${daily.score})"
            itemView.dailyitem_imageview.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(daily.video)
                itemView.context.startActivity(intent)

            }
        }
    }
}