package com.example.tottenham

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.playeritem.view.*

class PlayerRecyclerviewAdapter(val playerData: ArrayList<Player>) : RecyclerView.Adapter<PlayerRecyclerviewAdapter.Viewholder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayerRecyclerviewAdapter.Viewholder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.playeritem, parent, false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
        return playerData.size
    }

    override fun onBindViewHolder(holder: PlayerRecyclerviewAdapter.Viewholder, position: Int) {
        val player = playerData.get(position)
        holder.setPlayer(player)
    }

    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setPlayer(player : Player){
            Glide.with(itemView.context).load(player.image).into(itemView.player_imageview)
            itemView.player_num_text.text =  player.num.toString()
            itemView.player_name_text.text = player.name
            itemView.player_imageview.setOnClickListener {
                val intent = Intent(itemView.context, PlayerActivity::class.java)
                intent.putExtra("num", player.num)
                itemView.context.startActivity(intent)
            }
        }


    }
}