package com.example.deberspotifyclone

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerViewAdapterSong(
    private val songsArray: ArrayList<Song>,
    private val context: Context
): RecyclerView.Adapter<RecyclerViewAdapterSong.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSongName: TextView
        val tvSongArtist: TextView

        init {
            tvSongName = view.findViewById(R.id.tv_songName)
            tvSongArtist = view.findViewById(R.id.tv_songArtist)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_songs,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentSong = this.songsArray[position]
        holder.tvSongName.text = currentSong.name
        holder.tvSongArtist.text = currentSong.artist

        holder.itemView.setOnClickListener {
            /*
            val i = Intent(context, PlaylistDetailActivity::class.java)
            i.putExtra("id", currentSong.id)
            i.putExtra("nombre", currentSong.name)
            context.startActivity(i) */
        }
    }

    override fun getItemCount(): Int {
        return this.songsArray.size
    }
}