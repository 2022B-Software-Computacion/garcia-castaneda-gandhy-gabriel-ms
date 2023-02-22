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

class RecyclerViewAdapterPlaylist(
    private val playlistArray: ArrayList<Playlist>,
    private val context: Context
): RecyclerView.Adapter<RecyclerViewAdapterPlaylist.MyViewHolder>() {
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivPlaylist: ImageView
        val tvNamePlaylist: TextView
        val tvArtistsPlaylist: TextView

        init {
            ivPlaylist = view.findViewById(R.id.iv_playlist)
            tvNamePlaylist = view.findViewById(R.id.tv_playlist_name)
            tvArtistsPlaylist = view.findViewById(R.id.tv_playlist_artists)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapterPlaylist.MyViewHolder {
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

        holder.itemView.setOnClickListener {
            val i = Intent(context, PlaylistDetailActivity::class.java)
            i.putExtra("id", currentPlaylist.id)
            i.putExtra("name", currentPlaylist.name)
            i.putExtra("img", currentPlaylist.imageUrl)
            i.putExtra("artists", currentPlaylist.mainArtists)
            i.putParcelableArrayListExtra("songs", currentPlaylist.playlistSongs)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return this.playlistArray.size
    }

}