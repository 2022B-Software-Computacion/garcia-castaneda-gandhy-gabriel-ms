package com.example.deberspotifyclone

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

class RecyclerViewAdaptadorPlaylist(
    private val playlistArray: ArrayList<Playlist>,
    private val context: Context
): RecyclerView.Adapter<RecyclerViewAdaptadorPlaylist.MyViewHolder>() {
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivPlaylist: ImageView
        val tvNamePlaylist: TextView
        val tvArtistsPlaylist: TextView

        init {
            ivPlaylist = view.findViewById(R.id.iv_playlist)
            tvNamePlaylist = view.findViewById(R.id.tv_name_playlist)
            tvArtistsPlaylist = view.findViewById(R.id.tv_artists_playlist)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_playlist,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPlaylist = this.playlistArray[position]
        Picasso.get().load(currentPlaylist.imageUrl).into(holder.ivPlaylist)
        holder.tvNamePlaylist.text = currentPlaylist.name
        holder.tvArtistsPlaylist.text = currentPlaylist.mainArtists

        holder.itemView.setOnClickListener(View.OnClickListener {
            fun onClick(view: View){
                val i = Intent(context, PlaylistSongsActivity::class.java)
            }
        })
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}